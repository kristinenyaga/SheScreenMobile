package com.example.shescreen.ui.screens.RiskAssessment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.riskAssessment.PredictionResponse

@Composable
fun PredictionScreen(
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val prediction by viewModel.prediction.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // 🔹 Header
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "SheScreen",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Prediction Results",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
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
                    modifier = Modifier.height(28.dp)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.height(28.dp)
                )
            }
        }

        // 🔹 Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Your Personalized Assessment",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cluster
            PredictionItem(
                title = "Cluster",
                value = "Cluster ${prediction?.prediction?.cluster} / 5"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Interpretation
            PredictionItem(
                title = "Interpretation",
                value = prediction?.prediction?.interpretation.toString()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Recommended Screenings
            PredictionItem(
                title = "Recommended Screening",
                value = prediction?.prediction?.screening_recommendations?.recommended_screenings.toString()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Frequency
//            PredictionItem(
//                title = "Screening Frequency",
//                value = prediction.frequency
//            )

            Spacer(modifier = Modifier.height(12.dp))

            // Reason
            PredictionItem(
                title = "Reason for Recommendation",
                value = prediction?.summary?.reason.toString()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Urgency
            PredictionItem(
                title = "Risk Level",
                value = prediction?.summary?.risk_level.toString()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Done Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BAFBF))
            ) {
                Text("Done", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun PredictionItem(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF1A7F8F)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}