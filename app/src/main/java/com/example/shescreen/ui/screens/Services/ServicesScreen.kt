package com.example.shescreen.ui.screens.Services

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.example.shescreen.data.services.ServicesResponseItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.shescreen.data.bill.BillItem

@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val services by viewModel.services.collectAsState()
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Services", "Payments")

    LaunchedEffect(Unit) {
        viewModel.getServicesCost()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔹 Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF2BAFBF), Color(0xFF1A7F8F))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "SheScreen",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Cervical Health Companion",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    "Profile",
                    tint = Color.White,
                    modifier = Modifier.height(28.dp)
                )
                Icon(
                    Icons.Default.Notifications,
                    "Notifications",
                    tint = Color.White,
                    modifier = Modifier.height(28.dp)
                )
            }
        }

        // 🔹 Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        // 🔹 Body
        when (selectedTab) {
            0 -> {
                // Services Tab Content (your logic untouched)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    when {
                        services == null -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        services?.isEmpty() == true -> {
                            Text(
                                "No services available",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        else -> {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(services!!) { item ->
                                    ServiceCard(serviceItem = item)
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                PaymentsTab(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun ServiceCard(serviceItem: ServicesResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = serviceItem.service.name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF1A7F8F)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = serviceItem.service.description,
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Base Cost: KES ${serviceItem.base_cost}",
                    fontWeight = FontWeight.SemiBold
                )
                if (serviceItem.nhif_covered) {
                    Badge(
                        containerColor = Color(0xFF2BAFBF),
                        contentColor = Color.White
                    ) {
                        Text("NHIF Covered")
                    }
                }
            }

            Text(
                text = "Out-of-Pocket: KES ${serviceItem.out_of_pocket}",
                color = Color(0xFFE65100),
                fontWeight = FontWeight.Medium
            )

            serviceItem.insurance_copay_amount?.let {
                Text(
                    text = "Insurance Copay: KES $it",
                    color = Color(0xFF555555),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun PaymentsTab(viewModel: DataViewModel) {
    val bill by viewModel.bill.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBill()
    }

    when {
        bill == null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        bill?.items.isNullOrEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No pending payments.")
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(bill!!.items) { item ->
                        PaymentCard(item)
                    }
                }

                // Total + Pay Now Button
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Amount Due: KES ${bill!!.total_cost}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pay Now")
                    }
                }
            }
        }
    }
    if (showDialog && bill != null) {
        MpesaPaymentDialog(
            amount = bill!!.total_cost,
            onDismiss = { showDialog = false },
            onPay = { phoneNumber ->
                viewModel.initiateStkPush(phoneNumber, bill!!.total_cost.toInt())
                println("Phone: $phoneNumber | Amount: ${bill!!.total_cost}")
            }
        )
    }
}

@Composable
fun PaymentCard(item: BillItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.service.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF1A7F8F)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text("Base Cost: KES ${item.base_cost}")
            Text("NHIF Covered: ${if (item.nhif_covered) "Yes (KES ${item.nhif_amount})" else "No"}")
            Text("Out-of-Pocket: KES ${item.patient_amount}", color = Color(0xFFE65100))

            if (!item.paid) {
                Text(
                    text = "Status: Unpaid",
                    color = Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = "Status: Paid",
                    color = Color(0xFF2E7D32),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun MpesaPaymentDialog(
    amount: Double,
    onDismiss: () -> Unit,
    onPay: (String) -> Unit
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + scaleIn(initialScale = 0.9f),
        exit = fadeOut() + scaleOut()
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "M-PESA Payment",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Enter your phone number to complete payment")

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        placeholder = { Text("e.g. 254XXXXXXXXX") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Amount to pay: KES $amount",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1A7F8F)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onPay(phoneNumber.text)
                        Toast.makeText(
                            context,
                            "You'll be prompted to enter your M-PESA PIN",
                            Toast.LENGTH_LONG
                        ).show()
                        onDismiss()
                    },
                    enabled = phoneNumber.text.length == 12 // Optional validation
                ) {
                    Text("Pay with M-PESA")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(8.dp)
        )
    }
}


