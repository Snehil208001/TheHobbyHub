// snehil208001/thehobbyhub/TheHobbyHub-d30e7e1f3c7086c270533e7095de4d39ff2ade19/TheHobbyHub/app/src/main/java/com/example/thehobbyhub/mainui/signupscreen/ui/SignupScreen.kt
package com.example.thehobbyhub.mainui.signupscreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thehobbyhub.R
import com.example.thehobbyhub.core.navigations.Screen
import com.example.thehobbyhub.ui.theme.Gray7A7A7A
import com.example.thehobbyhub.ui.theme.LightGrayE0E0E0
import com.example.thehobbyhub.ui.theme.LightLavenderEDEAFF
import com.example.thehobbyhub.ui.theme.Purple6C63FF
import kotlinx.coroutines.delay

@Composable
fun SignupScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) }

    // Animation States
    var headerVisible by remember { mutableStateOf(false) }
    var formVisible by remember { mutableStateOf(false) }
    var socialVisible by remember { mutableStateOf(false) }
    var loginTextVisible by remember { mutableStateOf(false) }

    // Animation Specs (Smoothest settings)
    val smootherEasing = remember { CubicBezierEasing(0.2f, 0.0f, 0.2f, 1.0f) }
    val duration = 800 // base duration

    val floatAnimationSpec = tween<Float>(durationMillis = duration, easing = smootherEasing)
    val offsetAnimationSpec = tween<IntOffset>(durationMillis = duration, easing = smootherEasing)

    // Animation Sequence Trigger
    LaunchedEffect(Unit) {
        headerVisible = true // Logo/Title appears first
        delay(300)
        formVisible = true // Main form elements slide in
        delay(400)
        socialVisible = true // Social login/Divider appear
        delay(200)
        loginTextVisible = true // Bottom text appears last
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(LightLavenderEDEAFF, Color.White)
                    )
                )
        )
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                // Added verticalScroll to handle keyboard/small screens gracefully
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Logo and Title
            AnimatedVisibility(
                visible = headerVisible,
                enter = fadeIn(animationSpec = floatAnimationSpec) + slideInVertically(
                    initialOffsetY = { -it / 2 },
                    animationSpec = offsetAnimationSpec
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.thehobbyhub), // Assuming you have a logo resource
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(72.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        "Create an Account",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main Form Fields (Animated)
            AnimatedVisibility(
                visible = formVisible,
                enter = fadeIn(animationSpec = floatAnimationSpec) + slideInVertically(
                    initialOffsetY = { it / 6 },
                    animationSpec = offsetAnimationSpec
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = agreedToTerms,
                            onCheckedChange = { agreedToTerms = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        val annotatedString = buildAnnotatedString {
                            append("I agree to the ")
                            pushStringAnnotation(tag = "TERMS", annotation = "terms")
                            withStyle(style = SpanStyle(color = Purple6C63FF, fontWeight = FontWeight.Bold)) {
                                append("Terms of Service")
                            }
                            pop()
                            append(" and have read the ")
                            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                            withStyle(style = SpanStyle(color = Purple6C63FF, fontWeight = FontWeight.Bold)) {
                                append("Privacy Policy")
                            }
                            pop()
                        }
                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                                    .firstOrNull()?.let {
                                        navController.navigate(Screen.TermsOfService.route)
                                    }
                                annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                                    .firstOrNull()?.let {
                                        navController.navigate(Screen.PrivacyPolicy.route)
                                    }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { navController.navigate(Screen.CitySelection.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple6C63FF)
                    ) {
                        Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Social Login Section (Animated)
            AnimatedVisibility(
                visible = socialVisible,
                enter = fadeIn(animationSpec = floatAnimationSpec)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = LightGrayE0E0E0)
                        Text(
                            "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Gray7A7A7A,
                            fontWeight = FontWeight.SemiBold
                        )
                        Divider(modifier = Modifier.weight(1f), color = LightGrayE0E0E0)
                    }
                    OutlinedButton(
                        onClick = { /* TODO: Handle Google sign-in */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_google_logo),
                            contentDescription = "Google Logo",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "Continue with Google",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes content up when screen is tall

            // Login Text Button (Animated)
            AnimatedVisibility(
                visible = loginTextVisible,
                enter = fadeIn(animationSpec = floatAnimationSpec)
            ) {
                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Already have an account? Login")
                }
            }
        }
    }
}