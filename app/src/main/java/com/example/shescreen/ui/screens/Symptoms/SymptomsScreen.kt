package com.example.shescreen.ui.screens.Symptoms

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager
import com.example.shescreen.ui.navigation.PROFILE_SCREEN

@Composable
fun SymptomScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val context = LocalContext.current
    val token = PrefsManager(context).getAuthToken("token")

    var symptom by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
//    var isSubmitting by remember { mutableStateOf(false) }
//    var showSuccess by remember { mutableStateOf(false) }

    val severityOptions = (1..10).map { it.toString() }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8FFFE),
                        Color(0xFFE8F6F8)
                    )
                )
            )
    ) {
        // 🔹 Enhanced Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2BAFBF),
                            Color(0xFF1A7F8F),
                            Color(0xFF0F5A66)
                        )
                    )
                )
        ) {
            // Header content
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .align(Alignment.CenterStart),
            ) {
                Text(
                    text = "SheScreen",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Log Your Symptoms",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Profile icon
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { navController.navigate(PROFILE_SCREEN) },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Decorative elements
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 280.dp, bottom = 10.dp)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(40.dp)
                    )
            )
        }

        // 🔹 Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Title Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Log Your Symptoms",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A7F8F)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Help us better understand your health by recording your symptoms",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // Symptom Input
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Symptom Description",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A7F8F)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = symptom,
                        onValueChange = { symptom = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Describe your symptom (e.g., headache, nausea, fatigue)",
                                color = Color.Gray
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2BAFBF),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = Color(0xFF2BAFBF),
                            focusedTextColor = Color.Black,        // <--- Text color when focused
                            unfocusedTextColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            // Severity Dropdown - FIXED VERSION
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Severity Level",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A7F8F)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Fixed Dropdown Box
                    Box {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isDropdownExpanded = !isDropdownExpanded }
                                .border(
                                    1.dp,
                                    if (isDropdownExpanded) Color(0xFF2BAFBF) else Color.Gray.copy(
                                        alpha = 0.3f
                                    ),
                                    RoundedCornerShape(8.dp)
                                ),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (severity.isEmpty()) {
                                        "Select severity (1 = Mild, 10 = Severe)"
                                    } else {
                                        "Level $severity/10"
                                    },
                                    color = if (severity.isEmpty()) {
                                        Color.DarkGray
                                    } else {
                                        Color.Black
                                    },
                                    fontSize = 16.sp
                                )

                                Icon(
                                    imageVector = if (isDropdownExpanded)
                                        Icons.Default.KeyboardArrowUp
                                    else
                                        Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = Color(0xFF2BAFBF)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                        ) {
                            severityOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Level $option",
                                                fontWeight = FontWeight.Medium,
                                                color = Color.Gray
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = when (option.toInt()) {
                                                    in 1..3 -> "(Mild)"
                                                    in 4..6 -> "(Moderate)"
                                                    in 7..8 -> "(Severe)"
                                                    else -> "(Very Severe)"
                                                },
                                                color = when (option.toInt()) {
                                                    in 1..3 -> Color(0xFF4CAF50)
                                                    in 4..6 -> Color(0xFFFF9800)
                                                    in 7..8 -> Color(0xFFFF5722)
                                                    else -> Color(0xFFF44336)
                                                },
                                                fontSize = 14.sp
                                            )
                                        }
                                    },
                                    onClick = {
                                        severity = option
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Severity Visual Indicator
                    if (severity.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(10) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(1.dp)
                                        .background(
                                            color = if (index < severity.toInt()) {
                                                when (severity.toInt()) {
                                                    in 1..3 -> Color(0xFF4CAF50)
                                                    in 4..6 -> Color(0xFFFF9800)
                                                    in 7..8 -> Color(0xFFFF5722)
                                                    else -> Color(0xFFF44336)
                                                }
                                            } else Color.Gray.copy(alpha = 0.3f),
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }

            // Notes Input
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Additional Notes",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A7F8F)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = {
                            Text(
                                text = "Any additional details about your symptom (optional)",
                                color = Color.Gray.copy(alpha = 0.7f)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2BAFBF),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedLabelColor = Color(0xFF2BAFBF),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        maxLines = 4
                    )
                }
            }

            // Submit Button - FIXED VERSION
            Button(
                onClick = {
                    if (symptom.isNotEmpty() && severity.isNotEmpty()) {
//                        isSubmitting = true
                        viewModel.symptoms(
                            symptom = symptom,
                            severity = severity.toInt(),
                            notes = notes,
                            token = "Bearer $token",
                        )
                        symptom = ""
                        severity = ""
                        notes = ""
                        Toast.makeText(
                            context,
                            "Symptom logged successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please add your symptom and severity.",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2BAFBF),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Log Symptom",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            // Help Text
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color(0xFF2BAFBF).copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2BAFBF).copy(alpha = 0.05f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Tip",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A7F8F)
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Be as specific as possible when describing your symptoms. This helps healthcare providers give you better care.",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF1A7F8F).copy(alpha = 0.8f),
                            lineHeight = 20.sp
                        )
                    )
                }
            }

            // Add some bottom padding
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}