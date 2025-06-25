package com.example.shescreen.ui.screens.Auth

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.ui.navigation.BIO_DATA_SCREEN
import com.example.shescreen.ui.navigation.HOME_SCREEN
import com.example.shescreen.ui.navigation.SIGN_IN_SCREEN
import com.example.shescreen.ui.theme.SheScreenTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(navController: NavHostController, viewModel: DataViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    var context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Logo
        Text(
            text = "SheScreen",
            style = TextStyle(
                color = Color(0xFF2BAFBF),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = 0.33f,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF2BAFBF)
//            color = Color(0xFF2BAFBF),
//            backgroundColor = Color.LightGray.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 1/3",
            modifier = Modifier.align(Alignment.End),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Create your account !",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            placeholder = { Text("johndoe@example.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        Button(
            onClick = {
                if (password.value.isBlank() || confirmPassword.value.isBlank() || email.value.isBlank()) {
                    Toast.makeText(context, "Fill in all fields", Toast.LENGTH_LONG).show()
                } else if (password.value != confirmPassword.value) {
                    Toast.makeText(context, "Passwords don't match", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.signUp(email = email.toString(), password = password.toString())
                    navController.navigate(BIO_DATA_SCREEN)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BAFBF))
        ) {
            Text("Sign Up", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("or sign up with")

        Spacer(modifier = Modifier.height(12.dp))

        // Google Sign In Button (placeholder)
//        Box(
//            modifier = Modifier
//                .size(40.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(Color.LightGray)
//                .clickable { /* Google sign-in */ },
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_google_logo), // Add a vector or image asset
//                contentDescription = "Google",
//                tint = Color.Unspecified,
//                modifier = Modifier.size(24.dp)
//            )
//        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom text
        Row {
            Text("Have an account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "sign in",
                color = Color(0xFF2BAFBF),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(SIGN_IN_SCREEN)
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun SignUpScreenPreview() {
    SheScreenTheme {
        SignUpScreen(rememberNavController())
    }
}