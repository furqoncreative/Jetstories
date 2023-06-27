package id.furqoncreative.jetstories.ui.pages.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.ui.pages.login.components.LoginBody
import id.furqoncreative.jetstories.ui.pages.login.components.LoginHeader
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.showToast

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit,
    onClickSignup: () -> Unit,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSubmit = {
        if (uiState.emailState.isValid && uiState.passwordState.isValid) {
            loginViewModel.loginUser()
            keyboardController?.hide()
        }
    }

    LaunchedEffect(uiState.isSuccessLogin) {
        if (uiState.isSuccessLogin) {
            onSuccessLogin()
        }
    }

    uiState.userMessage?.let { userMessage ->
        context.showToast(message = userMessage.asString(context))
        loginViewModel.toastMessageShown()
    }

    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        LoginHeader(modifier = modifier)

        LoginBody(
            context = context,
            emailState = uiState.emailState,
            passwordState = uiState.passwordState,
            onSubmit = onSubmit,
            onClickSignup = onClickSignup,
            isLoading = uiState.isLoading,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreenPreview() {
    JetStoriesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
        ) {
            LoginScreen(onSuccessLogin = {}, onClickSignup = {})
        }
    }
}