// File: com/example/thehobbyhub/mainui/loginscreen/ui/LoginScreen.kt (UPDATED)
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
import androidx.compose.material3.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel // Import ViewModel function
import androidx.navigation.NavController
import com.example.thehobbyhub.R
import com.example.thehobbyhub.core.navigations.Screen
import com.example.thehobbyhub.mainui.loginscreen.viewmodel.LoginViewModel
import com.example.thehobbyhub.ui.theme.Gray7A7A7A
import com.example.thehobbyhub.ui.theme.LightGrayE0E0E0
import com.example.thehobbyhub.ui.theme.OffWhite
import com.example.thehobbyhub.ui.theme.Purple6C63FF
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel() // 1. Get ViewModel instance
) {
    // 2. Collect UI State
    val uiState by viewModel.uiState.collectAsState()

    // --- Animation States and Specs (REMAIN UNCHANGED) ---
    var logoAndTitleVisible by remember { mutableStateOf(false) }
    var mainFormVisible by remember { mutableStateOf(false) }
    var socialLoginVisible by remember { mutableStateOf(false) }
    var signUpTextVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val smootherEasing = remember { CubicBezierEasing(0.2f, 0.0f, 0.2f, 1.0f) }

    // Logo Animations (UNCHANGED)
    val logoInitialOffsetX = with(density) { (-40).dp.toPx() }
    val logoTargetOffsetX = 0f
    val logoTranslationX by animateFloatAsState(
        targetValue = if (logoAndTitleVisible) logoTargetOffsetX else logoInitialOffsetX,
        animationSpec = tween(durationMillis = 1200, easing = smootherEasing),
        label = "logoTranslationX"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (logoAndTitleVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = smootherEasing),
        label = "logoAlpha"
    )

    // Main Form Animations (UNCHANGED)
    val formInitialOffsetX = with(density) { (40).dp.toPx() }
    val formTargetOffsetX = 0f
    val formTranslationX by animateFloatAsState(
        targetValue = if (mainFormVisible) formTargetOffsetX else formInitialOffsetX,
        animationSpec = tween(durationMillis = 1200, easing = smootherEasing),
        label = "formTranslationX"
    )
    val formAlpha by animateFloatAsState(
        targetValue = if (mainFormVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = smootherEasing),
        label = "formAlpha"
    )

    // Animation Sequence Trigger (UNCHANGED)
    LaunchedEffect(Unit) {
        logoAndTitleVisible = true
        delay(800)
        mainFormVisible = true
        delay(800)
        socialLoginVisible = true
        delay(400)
        signUpTextVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Logo and Title (UNCHANGED) ---
            Spacer(modifier = Modifier.weight(0.5f))

            if (logoAndTitleVisible) {
                Image(
                    painter = painterResource(id = R.drawable.thehobbyhub),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .graphicsLayer {
                            alpha = logoAlpha
                            translationX = logoTranslationX
                        }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // --- Main UI Block (Input Fields, Buttons) ---
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
                    // 3. Connect UI to ViewModel State and Events
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEmailChange(it) },
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
                        value = uiState.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
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
                        onClick = { navController.navigate(Screen.ForgotPassword.route) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Display Error (if present)
                    uiState.error?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }

                    // Login Button uses ViewModel for onClick and state for enabled/loading
                    Button(
                        onClick = { viewModel.onLoginClicked(navController) },
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple6C63FF)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- "OR" Divider and Google Button (UNCHANGED structure, connect onClick) ---
            AnimatedVisibility(
                visible = socialLoginVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = smootherEasing))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Divider Row (UNCHANGED)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp),
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
                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedButton(
                        onClick = { viewModel.onGoogleSignInClicked() }, // Event handler
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

            Spacer(modifier = Modifier.weight(1f))

            // --- Bottom Text (connect onClick) ---
            AnimatedVisibility(
                visible = signUpTextVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = smootherEasing))
            ) {
                TextButton(onClick = { viewModel.onSignUpClicked(navController) }) { // Event handler
                    Text("Don't have an account? Sign up")
                }
            }
        }
    }
}