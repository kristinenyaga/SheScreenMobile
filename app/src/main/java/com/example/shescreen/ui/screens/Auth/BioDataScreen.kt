package com.example.shescreen.ui.screens.Auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager
import com.example.shescreen.ui.navigation.SIGN_IN_SCREEN
import com.example.shescreen.ui.theme.SheScreenTheme

@Composable
fun BioDataScreen(
    navController: NavHostController,
    prefsManager: PrefsManager,
    viewModel: DataViewModel = viewModel(),
) {
    val firstName = remember { mutableStateOf("Vicky") }
    val lastName = remember { mutableStateOf("Bish") }
    val phoneNumber = remember { mutableStateOf("0796358166") }
    val dateOfBirth = remember { mutableStateOf("2003-01-03") }
    val region = remember { mutableStateOf("Eldoret") }
    val isParent = remember { mutableStateOf<Boolean?>(true) }
    val context = LocalContext.current

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
            progress = 0.66f,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF2BAFBF),
            trackColor = Color.LightGray.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Step 2/3",
            modifier = Modifier.align(Alignment.End),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Personal Information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Please fill in the following details accurately",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = dateOfBirth.value,
            onValueChange = { dateOfBirth.value = it },
            label = { Text("Date Of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = region.value,
            onValueChange = { region.value = it },
            label = { Text("Region") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Are you a parent?", fontWeight = FontWeight.SemiBold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isParent.value == true,
                onClick = { isParent.value = true }
            )
            Text("Yes")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = isParent.value == false,
                onClick = { isParent.value = false }
            )
            Text("No")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val email = prefsManager.getUserDetail("email")
                val password = prefsManager.getUserDetail("password")
                val token = prefsManager.getAuthToken("token")
                viewModel.signIn(email.toString(), password.toString(), onSuccess = {
                    viewModel.profile(
                        dateOfBirth = dateOfBirth.value,
                        firstName = firstName.value,
                        lastName = lastName.value,
                        phoneNumber = phoneNumber.value,
                        isParent = isParent.value ?: false,
                        region = region.value,
                        token = "Bearer $token"
                    )
                    navController.navigate(SIGN_IN_SCREEN)
                }, context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BAFBF))
        ) {
            Text("Submit", color = Color.White, fontSize = 16.sp)
        }
    }
}
