package com.example.shescreen.ui.screens.Services

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


@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val services by viewModel.services.collectAsState()
    val context = LocalContext.current

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

        // 🔹 Body
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
                    Text("No services available", style = MaterialTheme.typography.bodyMedium)
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

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Base Cost: KES ${serviceItem.base_cost}", fontWeight = FontWeight.SemiBold)
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
