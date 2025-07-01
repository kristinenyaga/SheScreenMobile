package com.example.shescreen.ui.screens.RiskAssessment

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager

@Composable
fun RiskAssessmentScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DataViewModel = viewModel(),
    prefsManager: PrefsManager
) {
    val sexualPartners = remember { mutableStateOf("") }
    val firstSexualEncounter = remember { mutableStateOf("") }
    val smokes = remember { mutableStateOf<String?>(null) }
    val stdHistory = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header with gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2BAFBF),
                            Color(0xFF1A7F8F)
                        )
                    )
                )
        ) {
            // 🔹 Title & Subtitle (Centered)
            Column(
                modifier = Modifier
//                    .align(Alignment.Center)
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

            // 🔹 Icons at top-right
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.height(28.dp),
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.height(28.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Text(
                text = "Risk Assessment",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Please answer all questions accurately to get the best results.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = sexualPartners.value,
                onValueChange = { input ->
                    if (input.all { it.isDigit() } || input.isEmpty()) {
                        sexualPartners.value = input
                    }
                }, label = { Text("Number of sexual partners?") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = firstSexualEncounter.value,
                onValueChange = { input ->
                    if (input.all { it.isDigit() } || input.isEmpty()) {
                        firstSexualEncounter.value = input
                    }
                }, label = { Text("Age during first sexual encounter?") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Do you smoke?", fontWeight = FontWeight.SemiBold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = smokes.value == "Yes",
                    onClick = { smokes.value = "Yes" }
                )
                Text("Yes")

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = smokes.value == "No",
                    onClick = { smokes.value = "No" }
                )
                Text("No")
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text("Do you have a history of any std/sti?", fontWeight = FontWeight.SemiBold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = stdHistory.value == "Yes",
                    onClick = { stdHistory.value = "Yes" }
                )
                Text("Yes")

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = stdHistory.value == "No",
                    onClick = { stdHistory.value = "No" }
                )
                Text("No")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val partners = sexualPartners.value.toIntOrNull() ?: 0
                    val firstAge = firstSexualEncounter.value.toIntOrNull() ?: 0
                    val token = prefsManager.getAuthToken("token")
                    viewModel.riskAssessment(
                        sexualPartners = partners,
                        firstSexualIntercourseAge = firstAge,
                        smokingStatus = smokes.value ?: "No",
                        stdHistory = stdHistory.value ?: "No",
                        token = "Bearer $token"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BAFBF))
            ) {
                Text("Submit", color = Color.White, fontSize = 16.sp)
            }
        }
    }

}
