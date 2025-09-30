// snehil208001/thehobbyhub/TheHobbyHub-d30e7e1f3c7086c270533e7095de4d39ff2ade19/TheHobbyHub/app/src/main/java/com/example/thehobbyhub/mainui/loginscreen/ui/LoginScreen.kt
package com.example.thehobbyhub.mainui.loginscreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thehobbyhub.R
import com.example.thehobbyhub.ui.theme.Gray7A7A7A
import com.example.thehobbyhub.ui.theme.LightGrayE0E0E0
import com.example.thehobbyhub.ui.theme.Purple6C63FF
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Flags to control the staggered visibility of different UI sections
    var logoAndTitleVisible by remember { mutableStateOf(false) }
    var mainFormVisible by remember { mutableStateOf(false) }
    var socialLoginVisible by remember { mutableStateOf(false) }
    var signUpTextVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val smootherEasing = remember { CubicBezierEasing(0.2f, 0.0f, 0.2f, 1.0f) }

    // --- LOGO ANIMATIONS (Fade-in + Subtle Horizontal Shift Left-to-Right) ---
    val logoInitialOffsetX = with(density) { (-40).dp.toPx() } // Start 40dp left
    val logoTargetOffsetX = 0f

    val logoTranslationX by animateFloatAsState(
        targetValue = if (logoAndTitleVisible) logoTargetOffsetX else logoInitialOffsetX,
        animationSpec = tween(
            durationMillis = 1200, // A bit faster for the initial element
            easing = smootherEasing
        ),
        label = "logoTranslationX"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (logoAndTitleVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000, // Match fade with initial movement
            easing = smootherEasing
        ),
        label = "logoAlpha"
    )

    // --- MAIN FORM ANIMATIONS (Fade-in + Subtle Horizontal Shift Right-to-Left) ---
    val formInitialOffsetX = with(density) { (40).dp.toPx() } // Start 40dp right
    val formTargetOffsetX = 0f

    val formTranslationX by animateFloatAsState(
        targetValue = if (mainFormVisible) formTargetOffsetX else formInitialOffsetX,
        animationSpec = tween(
            durationMillis = 1200, // Match logo speed
            easing = smootherEasing
        ),
        label = "formTranslationX"
    )
    val formAlpha by animateFloatAsState(
        targetValue = if (mainFormVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000, // Match logo fade
            easing = smootherEasing
        ),
        label = "formAlpha"
    )

    // --- ANIMATION SEQUENCE TRIGGER ---
    LaunchedEffect(Unit) {
        // Logo appears first
        logoAndTitleVisible = true
        delay(800) // Small delay before main form starts
        // Main form (inputs, login button) appears
        mainFormVisible = true
        delay(800) // Small delay before social login
        // Social login (OR divider, Google button) appears
        socialLoginVisible = true
        delay(400) // Small delay before sign up text
        // Sign up text appears last
        signUpTextVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo (Animated)
            Spacer(modifier = Modifier.weight(0.5f)) // Adjust spacing

            if (logoAndTitleVisible) {
                Image(
                    painter = painterResource(id = R.drawable.thehobbyhub),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // Keep logo size reasonable
                        .graphicsLayer {
                            alpha = logoAlpha
                            translationX = logoTranslationX
                        }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Main UI Block (Input Fields, Buttons)
            AnimatedVisibility(
                visible = mainFormVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 800, easing = smootherEasing))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer {
                        alpha = formAlpha
                        translationX = formTranslationX
                    }
                ) {
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
                    TextButton(
                        onClick = { /* TODO: Handle forgot password navigation */ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("city_selection") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple6C63FF)
                    ) {
                        Text("Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp)) // Add space after form before social login

            // "OR" Divider and Google Button
            AnimatedVisibility(
                visible = socialLoginVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = smootherEasing))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp), // Reduce padding as Spacer handles vertical
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = LightGrayE0E0E0
                        )
                        Text(
                            "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Gray7A7A7A,
                            fontWeight = FontWeight.SemiBold
                        )
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = LightGrayE0E0E0
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp)) // Space before button

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

            Spacer(modifier = Modifier.weight(1f)) // Pushes content up to leave space for bottom text

            // Bottom Text
            AnimatedVisibility(
                visible = signUpTextVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = smootherEasing))
            ) {
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text("Don't have an account? Sign up")
                }
            }
        }
    }
}