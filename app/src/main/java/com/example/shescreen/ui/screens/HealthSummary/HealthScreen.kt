package com.example.shescreen.ui.screens.HealthSummary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shescreen.data.api.DataViewModel
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthScreen(
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val recommendations by viewModel.recommendation.collectAsState()
    val labTests by viewModel.labTest.collectAsState()
    val followUp by viewModel.followUp.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecommendation()
        viewModel.getLabTest()
        viewModel.getFollowUp()
    }

    val sortedRecs = recommendations?.sortedByDescending {
        ZonedDateTime.parse(it.created_at)
    } ?: emptyList()

    val groupedLabTests = labTests?.groupBy { it.recommendation_id } ?: emptyMap()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Enhanced Header
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .align(Alignment.CenterStart),
//                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "SheScreen",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "Comprehensive Cervical Health Management",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
//            Row(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Surface(
//                    shape = CircleShape,
//                    color = Color.White.copy(alpha = 0.15f),
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clickable { }
//                ) {
//                    Icon(
//                        Icons.Default.AccountCircle,
//                        contentDescription = "Profile",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .padding(8.dp)
//                    )
//                }
//                Surface(
//                    shape = CircleShape,
//                    color = Color.White.copy(alpha = 0.15f),
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clickable { }
//                ) {
//                    Icon(
//                        Icons.Default.Notifications,
//                        contentDescription = "Notifications",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .padding(8.dp)
//                    )
//                }
//            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { /* Handle profile click */ },
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
                IconButton(
                    onClick = { /* Handle notifications click */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enhanced Title Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Medical Records & Health Summary",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Your comprehensive health documentation",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF6B7280)
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enhanced Body Content
        if (sortedRecs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "📋",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No Health Records Available",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF374151)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Your health summary will be displayed here after your consultation with a healthcare provider.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF6B7280)
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sortedRecs) { rec ->
                    val associatedLabs = groupedLabTests[rec.id] ?: emptyList()
                    var expanded by remember { mutableStateOf(true) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(animationSpec = tween(300)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Header Section - using your original logic
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "Patient Record",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = Color(0xFF6B7280),
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "👤 ${rec.patient.first_name} ${rec.patient.last_name}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1A1A1A)
                                        )
                                    )
                                }
                                IconButton(
                                    onClick = { expanded = !expanded },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (expanded) "Collapse" else "Expand",
                                        tint = Color(0xFF2BAFBF)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Quick Info Section - using your original logic
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                InfoChip(
                                    label = "Priority",
                                    value = "🔥 Urgency: ${rec.urgency}",
                                    color = when (rec.urgency.lowercase()) {
                                        "high" -> Color(0xFFDC2626)
                                        "medium" -> Color(0xFFEA580C)
                                        "low" -> Color(0xFF059669)
                                        else -> Color(0xFF6B7280)
                                    }
                                )
                                InfoChip(
                                    label = "Date",
                                    value = "📅 ${rec.created_at.substring(0, 10)}",
                                    color = Color(0xFF6B7280)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Test Recommendations - using your original logic
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF3F4F6),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "🧪",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                    Column {
                                        Text(
                                            "Recommended Tests",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = Color(0xFF6B7280),
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Text(
                                            "🧪 Tests: ${rec.test_recommendations}",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color(0xFF374151),
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }

                            // Enhanced expandable content with your original logic
                            AnimatedVisibility(
                                visible = expanded,
                                enter = fadeIn(animationSpec = tween(300)),
                                exit = fadeOut(animationSpec = tween(200))
                            ) {
                                Column(modifier = Modifier.padding(top = 20.dp)) {
                                    // Notes Section - your original logic
                                    if (rec.notes.isNotBlank()) {
                                        Divider(
                                            modifier = Modifier.padding(vertical = 12.dp),
                                            color = Color(0xFFE5E7EB)
                                        )
                                        SectionHeader(title = "Clinical Notes", icon = "📝")
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color(0xFFFEF3C7),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                "🗒️ Notes: ${rec.notes}",
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = Color(0xFF92400E)
                                                ),
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }

                                    // Lab Results Section - your original logic
                                    if (associatedLabs.isNotEmpty()) {
                                        Divider(
                                            modifier = Modifier.padding(vertical = 16.dp),
                                            color = Color(0xFFE5E7EB)
                                        )
                                        SectionHeader(title = "Laboratory Results", icon = "🔬")
                                        Spacer(modifier = Modifier.height(12.dp))

                                        associatedLabs.forEach { lab ->
                                            val emoji = when (lab.result.toString().lowercase()) {
                                                "positive" -> "❌"
                                                "negative" -> "✅"
                                                "pending" -> "⏳"
                                                else -> "⚠️"
                                            }
                                            val (backgroundColor, textColor) = when (lab.result.toString().lowercase()) {
                                                "positive" -> Color(0xFFFEE2E2) to Color(0xFFDC2626)
                                                "negative" -> Color(0xFFD1FAE5) to Color(0xFF059669)
                                                "pending" -> Color(0xFFF3F4F6) to Color(0xFF6B7280)
                                                else -> Color(0xFFF3E8FF) to Color(0xFF7C3AED)
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))
                                            Card(
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(12.dp),
                                                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                                                elevation = CardDefaults.cardElevation(2.dp)
                                            ) {
                                                Column(modifier = Modifier.padding(16.dp)) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            "🧬 ${lab.service.name}",
                                                            style = MaterialTheme.typography.titleSmall.copy(
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color(0xFF1A1A1A)
                                                            )
                                                        )
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Text(
                                                                "$emoji ${lab.result}",
                                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                                    color = textColor,
                                                                    fontWeight = FontWeight.Bold
                                                                )
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        "Status: ${lab.status}",
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            color = Color(0xFF6B7280)
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        Text("No lab results found.", color = Color.Gray)
                                    }

                                    // Follow-up Section - your original logic
                                    Divider(
                                        modifier = Modifier.padding(vertical = 16.dp),
                                        color = Color(0xFFE5E7EB)
                                    )
                                    SectionHeader(title = "Follow-up Plan", icon = "📋")
                                    Spacer(modifier = Modifier.height(12.dp))

                                    if (followUp.toString().isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                                            elevation = CardDefaults.cardElevation(2.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(16.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        "📋",
                                                        fontSize = 18.sp,
                                                        modifier = Modifier.padding(end = 8.dp)
                                                    )
                                                    Text(
                                                        "Treatment Plan",
                                                        style = MaterialTheme.typography.titleSmall.copy(
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(0xFF1A1A1A)
                                                        )
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(12.dp))

                                                Text(
                                                    "📝 Final Plan: ${followUp?.final_plan}",
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = Color(0xFF1E40AF),
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                )

                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text("👩 Finalized By: ${followUp?.finalized_by?.first_name} ${followUp?.finalized_by?.last_name}",
                                                    color = Color(0xFF6B7280))
                                                Spacer(modifier = Modifier.height(6.dp))

                                                Row(Modifier.fillMaxWidth()) {
                                                    if (!followUp?.context.isNullOrEmpty()) {
                                                        Spacer(modifier = Modifier.height(6.dp))
                                                        Text(
                                                            "🧠 Context: ",
                                                            fontWeight = FontWeight.SemiBold,
                                                            color = Color(0xFF6B7280)
                                                        )

                                                        val contextText =
                                                            followUp!!.context.replace("[", "")
                                                                .replace("]", "")
                                                                .replace("\"", "")
                                                        Text(contextText,
                                                            color = Color(0xFF6B7280))
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Text("No follow-up data available.", color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun InfoChip(
    label: String,
    value: String,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                value,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            icon,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
        )
    }
}

