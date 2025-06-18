package com.example.shescreen.ui.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shescreen.R
import com.example.shescreen.ui.theme.SheScreenTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center
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
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Carousel / Pager
            CervicalInfoCarousel()

            // Clickable Options
            CervicalOption(
                title = "Cervical Cancer Screening",
                iconRes = R.drawable.ic_launcher_foreground, // Add your icon resources
                color = Color(0xFF4CAF50)
            ) {
                // navController.navigate("screening")
            }

            CervicalOption(
                title = "Cervical Cancer Management",
                iconRes = R.drawable.ic_launcher_background,
                color = Color(0xFF2196F3)
            ) {
                // navController.navigate("management")
            }

            CervicalOption(
                title = "Cervical Cancer Prevention",
                iconRes = R.drawable.ic_launcher_foreground,
                color = Color(0xFF9C27B0)
            ) {
                // navController.navigate("prevention")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CervicalInfoCarousel() {
    val pagerState = rememberPagerState()
    val pages = listOf(
        CarouselItem(
            text = "Regular screening can detect cervical cancer early when it's most treatable",
            imageRes = R.drawable.ic_launcher_foreground  // Add your image resources
        ),
        CarouselItem(
            text = "HPV vaccination can prevent most cases of cervical cancer",
            imageRes = R.drawable.ic_launcher_background
        ),
        CarouselItem(
            text = "Know your risk factors and get screened regularly",
            imageRes = R.drawable.ic_launcher_foreground
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = pages[page].imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0.5f
                            )
                        )
                )

                Text(
                    text = pages[page].text,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = Color(0xFF2BAFBF),
            inactiveColor = Color.LightGray
        )
    }
}

@Composable
fun CervicalOption(
    title: String,
    iconRes: Int,
    color: Color,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.2f))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Add arrow icon
                contentDescription = "Navigate",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

data class CarouselItem(
    val text: String,
    val imageRes: Int
)

@Preview
@Composable
private fun SignUpScreenPreview() {
    SheScreenTheme {
        HomeScreen(navController = rememberNavController())
    }
}