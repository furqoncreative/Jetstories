package id.furqoncreative.jetstories.ui.screens.login

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesCircularProgressBar
import id.furqoncreative.jetstories.ui.components.JetstoriesEmailTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesPasswordTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesSnackBarHost
import id.furqoncreative.jetstories.ui.components.showSnackBar
import id.furqoncreative.jetstories.ui.components.states.PasswordState
import id.furqoncreative.jetstories.ui.components.states.TextFieldState
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            onNavigateToHome()
        }
    }

    val onSubmit = {
        if (uiState.emailState.isValid && uiState.passwordState.isValid) {
            loginViewModel.loginUser()
            keyboardController?.hide()
        }
    }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let {
            showSnackBar(
                snackbarHostState = snackbarHostState,
                actionLabel = "OK",
                message = it.asString(context),
            )
        }
        loginViewModel.toastMessageShown()
    }

    Box(
        modifier = modifier.fillMaxHeight(),
    ) {
        JetstoriesSnackBarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()

            LoginContent(
                context = context,
                emailState = uiState.emailState,
                passwordState = uiState.passwordState,
                onSubmit = onSubmit,
                onClickSignup = onNavigateToRegister,
                isLoading = uiState.isLoading,
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreenPreview() {
    JetStoriesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
        ) {
            LoginScreen(
                onNavigateToHome = {},
                onNavigateToRegister = {},
                loginViewModel = hiltViewModel()
            )
        }
    }
}

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .height(300.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LaunchedEffect(key1 = visible) {
                delay(500)
                visible = true
            }

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    animationSpec = tween(1500)
                ) + fadeIn(initialAlpha = 0.3f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jetstories_logo),
                    contentDescription = "Jetstories Logo",
                    modifier = Modifier.size(125.dp)
                )
            }

            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(
                    initialScale = 0.3f, animationSpec = tween(1500)
                ) + fadeIn(initialAlpha = 0.3f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = stringResource(id = R.string.app_tagline),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginHeaderPreview() {
    LoginHeader()
}

@Composable
fun LoginContent(
    context: Context,
    emailState: TextFieldState,
    passwordState: PasswordState,
    onSubmit: () -> Unit,
    onClickSignup: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val commonModifier = Modifier.fillMaxWidth()

        JetstoriesEmailTextField(
            modifier = commonModifier,
            context = context,
            emailState = emailState
        )

        JetstoriesPasswordTextField(
            modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder),
            passwordState = passwordState,
            onImeAction = {
                onSubmit()
            }
        )

        Row(
            modifier = commonModifier.align(Alignment.End),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = stringResource(R.string.dont_have_an_account)
            )
            Text(
                text = stringResource(id = R.string.sign_up),
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onClickSignup()
                    }
            )
        }

        Button(
            modifier = commonModifier.height(56.dp),
            enabled = emailState.isValid && passwordState.isValid && !isLoading,
            onClick = {
                onSubmit()
            }
        ) {
            if (!isLoading) {
                Text(text = stringResource(id = R.string.sign_in))
            } else {
                JetstoriesCircularProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginBodyPreview() {
    JetStoriesTheme {
        LoginContent(context = LocalContext.current,
            emailState = TextFieldState(),
            passwordState = PasswordState(),
            onSubmit = {},
            onClickSignup = {})
    }
}