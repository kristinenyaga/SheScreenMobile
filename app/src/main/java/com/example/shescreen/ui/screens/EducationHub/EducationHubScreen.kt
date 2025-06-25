package com.example.shescreen.ui.screens.EducationHub

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shescreen.R
import com.example.shescreen.ui.screens.Home.CarouselItem

data class CarouselCategory(
    val title: String,
    val items: List<CarouselItem>
)

@Composable
fun EducationHubScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    // ✅ Dummy data
    val categories = remember {
        listOf(
            CarouselCategory(
                title = "Cervical Cancer",
                items = listOf(
                    CarouselItem(0, R.drawable.prop, "What is cervical cancer?"),
                    CarouselItem(1, R.drawable.prop, "Symptoms and signs"),
                    CarouselItem(2, R.drawable.prop, "Causes and risk factors")
                )
            ),
            CarouselCategory(
                title = "Treatment",
                items = listOf(
                    CarouselItem(1, R.drawable.prop, "Treatment options"),
                    CarouselItem(2, R.drawable.prop, "Chemotherapy"),
                    CarouselItem(3, R.drawable.prop, "Surgery")
                )
            ),
            CarouselCategory(
                title = "Prevention",
                items = listOf(
                    CarouselItem(1, R.drawable.prop, "HPV Vaccination"),
                    CarouselItem(2, R.drawable.prop, "Routine screening"),
                    CarouselItem(3, R.drawable.prop, "Healthy lifestyle")
                )
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Enables scrolling when many carousels
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
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "SheScreen",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cervical Health Companion",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Text(
                text = "Education Hub",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔄 Loop through each category and show carousel
            categories.forEach { category ->
                TopicCarousel(title = category.title, items = category.items)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicCarousel(title: String, items: List<CarouselItem>) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { items.size },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            preferredItemWidth = 220.dp,
            itemSpacing = 8.dp
        ) { index ->
            val item = items[index]
            Box(
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Gray) // 🔁 Replace with image later
            ) {
                // 🔁 Replace with Firebase image:
                // AsyncImage(model = item.imageUrl, ...)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.75f)
                ) {
                    // Placeholder image or color for now
                    Image(
                        painter = painterResource(id = item.imageResId),
                        contentDescription = item.contentDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.75f)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = item.contentDescription,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
