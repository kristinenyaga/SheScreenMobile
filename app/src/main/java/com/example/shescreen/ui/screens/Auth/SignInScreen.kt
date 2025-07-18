package com.example.shescreen.ui.screens.Auth

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager
import com.example.shescreen.ui.navigation.HOME_SCREEN
import com.example.shescreen.ui.navigation.SIGN_UP_SCREEN
import com.example.shescreen.ui.theme.SheScreenTheme

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: DataViewModel = viewModel()
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Theme colors
    val primaryColor = Color(0xFF2BAFBF)
    val primaryVariant = Color(0xFF1E8A96)
    val backgroundColor = Color(0xFFF8FFFE)
    val surfaceColor = Color.White
    val errorColor = Color(0xFFE74C3C)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Logo with gradient background
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(primaryColor, primaryVariant)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SS",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SheScreen",
                style = TextStyle(
                    color = primaryColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Progress indicator with better styling
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                colors = CardDefaults.cardColors(containerColor = surfaceColor),
//                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    LinearProgressIndicator(
//                        progress = 1f,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(6.dp)
//                            .clip(RoundedCornerShape(3.dp)),
//                        color = primaryColor,
//                        trackColor = Color.LightGray.copy(alpha = 0.3f)
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = "Setup Complete",
//                        fontSize = 14.sp,
//                        color = primaryColor,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            }

            Spacer(modifier = Modifier.height(32.dp))

            // Welcome message
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign in to continue your health journey",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Input fields card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Email field
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                            if (errorMessage.isNotEmpty()) errorMessage = ""
                        },
                        label = { Text("Email Address") },
                        placeholder = { Text("Enter your email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(
                            Color(0xFF1A1A1A)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                            if (errorMessage.isNotEmpty()) errorMessage = ""
                        },
                        label = { Text("Password") },
                        placeholder = { Text("Enter your password") },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible)
                                        "Hide password" else "Show password",
                                    tint = primaryColor
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor,
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(
                            Color(0xFF1A1A1A)
                        )
                    )

                    // Error message
                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = errorColor,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot password
            TextButton(
                onClick = { /* Handle forgot password */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot Password?",
                    color = primaryColor,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign In Button
            Button(
                onClick = {
                    if (email.value.isBlank() || password.value.isBlank()) {
                        errorMessage = "Please fill in all fields"
                        return@Button
                    }

                    isLoading = true
                    errorMessage = ""

                    viewModel.signIn(
                        email = email.value,
                        password = password.value,
                        onSuccess = {
                            isLoading = false
                            navController.navigate(HOME_SCREEN)
                        },
                        context
                    )
                    navController.navigate(HOME_SCREEN)
                    Toast.makeText(
                        context,
                        "Sign In Successful",
                        Toast.LENGTH_LONG
                    ).show()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    disabledContainerColor = primaryColor.copy(alpha = 0.6f)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Sign In",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

//            // Divider
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .height(1.dp)
//                        .background(Color.LightGray.copy(alpha = 0.5f))
//                )
//                Text(
//                    text = "  or sign in with  ",
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .height(1.dp)
//                        .background(Color.LightGray.copy(alpha = 0.5f))
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))

//            // Social login placeholder
//            Card(
//                modifier = Modifier
//                    .size(56.dp)
//                    .clickable { /* Google sign-in */ },
//                colors = CardDefaults.cardColors(containerColor = surfaceColor),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                shape = RoundedCornerShape(28.dp)
//            ) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "G",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = primaryColor
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // Bottom navigation
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Don't have an account? ",
//                    color = Color.Gray,
//                    fontSize = 16.sp
//                )
//                TextButton(
//                    onClick = { navController.navigate(SIGN_UP_SCREEN) }
//                ) {
//                    Text(
//                        text = "Sign Up",
//                        color = primaryColor,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    )
//                }
//            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}