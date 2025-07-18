package com.example.shescreen.ui.screens.EducationHub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shescreen.data.cms.SupabaseInit
import com.example.shescreen.data.cms.EducationalContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDetailScreen(
    contentId: Int,
    navController: NavHostController
) {
    var content by remember { mutableStateOf<EducationalContent?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(contentId) {
        val allContent = SupabaseInit.getEducationalContent()
        content = allContent.find { it.id == contentId }
        isLoading = false
    }

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
        // 🔹 Enhanced Header with Back Button
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
            // Back button
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Header content
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .align(Alignment.BottomStart),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "SheScreen",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Health Education",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Decorative elements
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 280.dp, y = (-20).dp)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(50.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(x = 320.dp, y = 40.dp)
                    .background(
                        Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(30.dp)
                    )
            )
        }

        // 🔹 Content Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF2BAFBF),
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading content...",
                            color = Color(0xFF1A7F8F),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                content?.let { educationContent ->
                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔹 Content Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = Color(0xFF2BAFBF).copy(alpha = 0.1f)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(0.dp)
                        ) {
                            // Image with rounded corners
                            AsyncImage(
                                model = educationContent.image,
                                contentDescription = educationContent.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                                    .background(Color.LightGray.copy(alpha = 0.3f))
                            )

                            // Content details
                            Column(
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(
                                    text = educationContent.title,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        color = Color(0xFF1A7F8F),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Subtle divider
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(3.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    Color(0xFF2BAFBF),
                                                    Color(0xFF2BAFBF).copy(alpha = 0.3f)
                                                )
                                            ),
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = educationContent.description,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color(0xFF2D3748),
                                        lineHeight = 26.sp,
                                        fontSize = 16.sp
                                    ),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 🔹 Additional Info Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color(0xFF2BAFBF).copy(alpha = 0.1f)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF0FFFE)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "💡 Key Takeaways",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color(0xFF1A7F8F),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "This educational content is designed to help you make informed decisions about your health and wellness.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF4A5568),
                                    lineHeight = 22.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                } ?: run {
                    // 🔹 Error State
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 40.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF5F5)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "⚠️",
                                fontSize = 40.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Content Not Found",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color(0xFFE53E3E),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "The requested educational content could not be found. Please try again later.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF742A2A),
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}