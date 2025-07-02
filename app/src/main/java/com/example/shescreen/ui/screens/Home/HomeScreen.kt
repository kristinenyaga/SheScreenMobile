package com.example.shescreen.ui.screens.Home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.shescreen.ui.navigation.CHAT_SCREEN
import com.example.shescreen.ui.navigation.EDUCATION_HUB_SCREEN
import com.example.shescreen.ui.navigation.RISK_ASSESSMENT_SCREEN
import com.example.shescreen.ui.theme.SheScreenTheme

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.prop, "cupcake"),
            CarouselItem(1, R.drawable.prop, "donut"),
            CarouselItem(2, R.drawable.prop, "eclair"),
            CarouselItem(3, R.drawable.prop, "froyo"),
            CarouselItem(4, R.drawable.prop, "gingerbread"),
        )
    }
    var showDialog by remember { mutableStateOf(false) }


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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Text(
                "Welcome User 👋", style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            //            Spacer(modifier.height(20.dp))
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { items.count() },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp, bottom = 16.dp),
                preferredItemWidth = 250.dp,
                itemSpacing = 8.dp,
//                contentPadding = PaddingValues(horizontal = 10.dp)
            ) { i ->
                val item = items[i]
                Image(
                    modifier = Modifier
                        .height(250.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge),
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "What would you like to do?",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardItem(
                    title = "Risk Assessment",
                    description = "Evaluate your health risk through guided questions and early warning signs.",
                    modifier = Modifier.weight(1f),
                    route = RISK_ASSESSMENT_SCREEN,
                    navController = navController
                )

                CardItem(
                    title = "Services",
                    description = "Locate nearby hospitals, clinics, and support centers with ease.",
                    modifier = Modifier.weight(1f),
                    route = "",
                    navController = navController
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardItem(
                    title = "Education Hub",
                    description = "Access trusted information about cervical cancer, symptoms, and prevention.",
                    modifier = Modifier.weight(1f),
                    route = EDUCATION_HUB_SCREEN,
                    navController = navController
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FloatingActionButton(
                onClick = {
                    showDialog = true
                    navController.navigate(CHAT_SCREEN)
                },
                containerColor = Color(0xFF1A7F8F),
                contentColor = Color(0xFF2BAFBF),
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Textsms,
                    contentDescription = "Message"
                )
            }
        }
    }
}

@Composable
fun CardItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    route: String,
    navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F8F8)),
        modifier = modifier
            .height(140.dp)
            .clickable { navController.navigate(route) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A7F8F)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            )
        }
    }
}


@Preview
@Composable
private fun SignUpScreenPreview() {
    SheScreenTheme {
        HomeScreen(navController = rememberNavController())
    }
}