package com.example.shescreen.ui.screens.Home

import android.content.Context
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.shescreen.R
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.messaging.BackgroundFetchWorker
import com.example.shescreen.ui.navigation.CHAT_SCREEN
import com.example.shescreen.ui.navigation.EDUCATION_HUB_SCREEN
import com.example.shescreen.ui.navigation.HEALTH_SCREEN
import com.example.shescreen.ui.navigation.PROFILE_SCREEN
import com.example.shescreen.ui.navigation.RISK_ASSESSMENT_SCREEN
import com.example.shescreen.ui.navigation.SERVICES_SCREEN
import com.example.shescreen.ui.navigation.SYMPTOMS_SCREEN
import com.example.shescreen.ui.screens.EducationHub.CarouselItem
import com.example.shescreen.ui.theme.SheScreenTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

data class CarouselItems(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String,
    val title: String,
    val subtitle: String
)

data class QuickAction(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: String,
    val backgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val context = LocalContext.current

    // Demo/testing work manager code
    LaunchedEffect(Unit) {
        fun scheduleOneTimeFetch() {
            val fetchRequest = OneTimeWorkRequestBuilder<BackgroundFetchWorker>()
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueue(fetchRequest)
        }

        scheduleOneTimeFetch()

        repeat(100) {
            delay(10000)
            scheduleOneTimeFetch()
        }
    }

    // Enhanced carousel items with health-related content
    val items = remember {
        listOf(
            CarouselItems(
                0,
                R.drawable.cancer ,
                "Cervical Health Screening",
                "Stay Protected",
                "Regular screening saves lives"
            ),
            CarouselItems(
                1,
                R.drawable.prev,
                "Prevention Tips",
                "Prevention First",
                "Simple steps for better health"
            ),
            CarouselItems(
                2,
                R.drawable.prop,
                "Health Education",
                "Know Your Body",
                "Understanding cervical health"
            ),
            CarouselItems(
                3,
                R.drawable.alone,
                "Support Community",
                "You're Not Alone",
                "Connect with others"
            ),
            CarouselItems(
                4,
                R.drawable.pref,
                "Expert Care",
                "Professional Support",
                "Access quality healthcare"
            ),
        )
    }

    // Quick actions for better UX
    val quickActions = remember {
        listOf(
            QuickAction(
                "Book Appointment",
                "Schedule your screening",
                Icons.Default.Schedule,
                SERVICES_SCREEN,
                Color(0xFF4CAF50)
            ),
            QuickAction(
                "Emergency",
                "Get immediate help",
                Icons.Default.LocalHospital,
                HEALTH_SCREEN,
                Color(0xFFE91E63)
            ),
            QuickAction(
                "Wellness Tips",
                "Daily health advice",
                Icons.Default.Favorite,
                EDUCATION_HUB_SCREEN,
                Color(0xFF9C27B0)
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FFFE))
    ) {
        // Enhanced Header with better spacing and styling
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2BAFBF),
                            Color(0xFF1A7F8F),
                            Color(0xFF0D5F6F)
                        )
                    )
                )
        ) {
            // Title & Subtitle
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .align(Alignment.CenterStart),
            ) {
                Text(
                    text = "SheScreen",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                )
                Text(
                    text = "Your Cervical Health Companion",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            // Decorative elements
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .offset(x = 280.dp, y = (-20).dp)
//                    .background(
//                        Color.White.copy(alpha = 0.1f),
//                        shape = RoundedCornerShape(50.dp)
//                    )
//            )
//            Box(
//                modifier = Modifier
//                    .size(60.dp)
//                    .offset(x = 320.dp, y = 40.dp)
//                    .background(
//                        Color.White.copy(alpha = 0.05f),
//                        shape = RoundedCornerShape(30.dp)
//                    )
//            )

            // Enhanced top-right icons with notification badge
//            Row(
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Box {
//                    Icon(
//                        imageVector = Icons.Default.Notifications,
//                        contentDescription = "Notifications",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(28.dp)
//                            .clickable { /* Handle notification click */ }
//                    )
//                    // Notification badge
//                    Box(
//                        modifier = Modifier
//                            .size(8.dp)
//                            .clip(CircleShape)
//                            .background(Color.Red)
//                            .align(Alignment.TopEnd)
//                    )
//                }
//
//                Icon(
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = "Profile",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clickable { /* Handle profile click */ }
//                )
//            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { /* Handle profile click */
                    navController.navigate(PROFILE_SCREEN)},
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
//                IconButton(
//                    onClick = { /* Handle notifications click */ },
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(
//                            Color.White.copy(alpha = 0.15f),
//                            shape = RoundedCornerShape(20.dp)
//                        )
//                ) {
//                    Icon(
//                        Icons.Default.Notifications,
//                        "Notifications",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            // Enhanced welcome message
            Text(
                "Welcome back! 👋",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF1A7F8F)
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Text(
                "How can we help you today?",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Enhanced carousel with overlay text
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { items.count() },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp),
                preferredItemWidth = 280.dp,
                itemSpacing = 12.dp,
            ) { i ->
                val item = items[i]
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        painter = painterResource(id = item.imageResId),
                        contentDescription = item.contentDescription,
                        contentScale = ContentScale.Crop
                    )

                    // Gradient overlay for better text visibility
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                    )

                    // Overlay text
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = item.subtitle,
                            style = TextStyle(
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }

//            // Quick Actions Row
//            Text(
//                text = "Quick Actions",
//                style = TextStyle(
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    color = Color(0xFF1A7F8F)
//                ),
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 20.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                quickActions.forEach { action ->
//                    QuickActionItem(
//                        action = action,
//                        navController = navController,
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }

            // Main action cards
            Text(
                text = "What we Offer",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF1A7F8F)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedCardItem(
                    title = "Education Hub",
                    description = "Access information about cervical cancer, symptoms, and prevention strategies.",
                    icon = "📚",
                    modifier = Modifier.weight(1f),
                    route = EDUCATION_HUB_SCREEN,
                    navController = navController
                )

                EnhancedCardItem(
                    title = "Services and Payments",
                    description = "View the services we offer and their prices and make your payments easily with one click.",
                    icon = "🏥",
                    modifier = Modifier.weight(1f),
                    route = SERVICES_SCREEN,
                    navController = navController
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedCardItem(
                    title = "My Health Summary",
                    description = "View your recent recommendations, lab test results and follow-ups.",
                    icon = "📊",
                    modifier = Modifier.weight(1f),
                    route = HEALTH_SCREEN,
                    navController = navController
                )

                EnhancedCardItem(
                    title = "Log Symptoms",
                    description = "Record your symptoms to help monitor your health status.",
                    icon = "📝",
                    modifier = Modifier.weight(1f),
                    route = SYMPTOMS_SCREEN,
                    navController = navController
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Enhanced FAB
            FloatingActionButton(
                onClick = {
                    showDialog = true
                    navController.navigate(CHAT_SCREEN)
                },
                containerColor = Color(0xFF1A7F8F),
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.End)
                    .shadow(12.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Textsms,
                    contentDescription = "Chat Support",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(
    action: QuickAction,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = action.backgroundColor.copy(alpha = 0.1f)),
        modifier = modifier
            .padding(horizontal = 4.dp)
            .height(80.dp)
            .clickable { navController.navigate(action.route) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                tint = action.backgroundColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = action.title,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = action.backgroundColor
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EnhancedCardItem(
    title: String,
    description: String,
    icon: String,
    modifier: Modifier = Modifier,
    route: String,
    navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F8F8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .height(160.dp)
            .clickable { navController.navigate(route) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A7F8F)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = icon,
                    style = TextStyle(fontSize = 24.sp)
                )
            }

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    SheScreenTheme {
        HomeScreen(navController = rememberNavController())
    }
}