package com.example.shescreen.ui.screens.HealthSummary


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.labtests.LabTestResponse
import com.example.shescreen.data.recommendation.RecommendationResponseItem


//@Composable
//fun HealthScreen(
//    navController: NavHostController,
//    viewModel: DataViewModel = viewModel()
//) {
//    val recommendations by viewModel.recommendation.collectAsState()
//    val labTests by viewModel.labTest.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.getRecommendation()
//        viewModel.getLabTest()
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//
//        Text("Recommendations", style = MaterialTheme.typography.titleLarge)
//
//        recommendations?.forEach { rec ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                Column(modifier = Modifier.padding(12.dp)) {
//                    Text("Patient: ${rec.patient.first_name} ${rec.patient.last_name}")
//                    Text("Urgency: ${rec.urgency}")
//                    Text("AI Recommendation: ${rec.ai_recommendation}")
//                    Text("Test Recommendations: ${rec.test_recommendations}")
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//        Text("Lab Tests", style = MaterialTheme.typography.titleLarge)
//
//        labTests?.forEach { lab ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                Column(modifier = Modifier.padding(12.dp)) {
//                    Text("Patient: ${lab.patient.first_name} ${lab.patient.last_name}")
//                    Text("Test: ${lab.service.name}")
//                    Text("Status: ${lab.status}")
//                    Text("Date Ordered: ${lab.date_ordered}")
//                }
//            }
//        }
//    }
//}
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import kotlinx.datetime.LocalDateTime

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthScreen(
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val recommendations by viewModel.recommendation.collectAsState()
    val labTests by viewModel.labTest.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecommendation()
        viewModel.getLabTest()
    }

    val sortedRecs = recommendations?.sortedByDescending {
        ZonedDateTime.parse(it.created_at)
    } ?: emptyList()

    val groupedLabTests = labTests?.groupBy { it.recommendation_id } ?: emptyMap()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
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
                    "SheScreen",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "Cervical Health Companion",
                    style = TextStyle(color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Health Summary", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
            ),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )


        // Body
        if (sortedRecs.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "You have no health summary available. \nThis will appear after consulting with a doctor.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(sortedRecs) { rec ->
                    val associatedLabs = groupedLabTests[rec.id] ?: emptyList()
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { expanded = !expanded },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "👤 ${rec.patient.first_name} ${rec.patient.last_name}",
                                fontWeight = FontWeight.Bold
                            )
                            Text("🔥 Urgency: ${rec.urgency}", color = Color(0xFFD84315))
                            Text("🧪 Tests: ${rec.test_recommendations}")
                            Text("📅 Recommended: ${rec.created_at.substring(0, 10)}")

                            AnimatedVisibility(visible = expanded) {
                                Column(modifier = Modifier.padding(top = 12.dp)) {
                                    if (rec.notes.isNotBlank()) {
                                        Text("🗒️ Notes: ${rec.notes}")
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    if (associatedLabs.isNotEmpty()) {
                                        Text(
                                            "🔬 Lab Results",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        associatedLabs.forEach { lab ->
                                            val emoji = when (lab.result.toString().lowercase()) {
                                                "positive" -> "❌"
                                                "negative" -> "✅"
                                                "pending" -> "⏳"
                                                else -> "⚠️"
                                            }
                                            val color = when (lab.result.toString().lowercase()) {
                                                "positive" -> Color.Red
                                                "negative" -> Color(0xFF2E7D32)
                                                "pending" -> Color.Gray
                                                else -> Color(0xFF6A1B9A)
                                            }

                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                colors = CardDefaults.cardColors(),
                                                elevation = CardDefaults.cardElevation(2.dp)
                                            ) {
                                                Column(modifier = Modifier.padding(12.dp)) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Text("🧬 ${lab.service.name}")
                                                        Text(
                                                            "$emoji ${lab.result}",
                                                            color = color,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }
                                                    Text("Status: ${lab.status}")
                                                    if (!lab.date_completed.toString()
                                                            .isNullOrEmpty()
                                                    ) {
                                                        Text(
                                                            "Completed: ${
                                                                lab.date_completed.toString()
                                                                    .substring(0, 10)
                                                            }"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Text("No lab results found.", color = Color.Gray)
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        "📌 Follow-up",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text("Follow-up details coming soon...", color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


