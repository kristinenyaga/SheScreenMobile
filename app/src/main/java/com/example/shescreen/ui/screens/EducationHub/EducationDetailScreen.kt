package com.example.shescreen.ui.screens.EducationHub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shescreen.data.cms.SupabaseInit
import com.example.shescreen.data.cms.EducationalContent

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
            .verticalScroll(rememberScrollState())
    ) {
        // 🔹 Gradient Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF2BAFBF), Color(0xFF1A7F8F))
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
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
                )
                Text(
                    text = "Health Education",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF2BAFBF))
            }
        } else {
            content?.let {
                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = it.image,
                    contentDescription = it.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 16.dp)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color(0xFF1A7F8F),
                            fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            } ?: run {
                Text(
                    "Content not found.",
                    modifier = Modifier.padding(24.dp),
                    color = Color.Red
                )
            }
        }
    }
}
