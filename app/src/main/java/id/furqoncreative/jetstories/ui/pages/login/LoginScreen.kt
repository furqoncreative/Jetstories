package id.furqoncreative.jetstories.ui.pages.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.furqoncreative.jetstories.ui.components.EmailState
import id.furqoncreative.jetstories.ui.components.EmailStateSaver
import id.furqoncreative.jetstories.ui.components.PasswordState
import id.furqoncreative.jetstories.ui.pages.login.components.LoginBody
import id.furqoncreative.jetstories.ui.pages.login.components.LoginHeader
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)

    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }

    val passwordState = remember { PasswordState() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSubmit = {
        if (emailState.isValid && passwordState.isValid) {
            loginViewModel.loginUser(
                emailState.text, passwordState.text
            )
            keyboardController?.hide()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        when (loginViewModel.loginUiState) {
            is LoginUiState.Loading -> {
                LinearProgressIndicator()
            }

            is LoginUiState.Success -> {
                Toast.makeText(LocalContext.current, "Login", Toast.LENGTH_LONG).show()
            }

            is LoginUiState.Error -> {
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
            }

            LoginUiState.Idle -> {

            }
        }

        LoginHeader(modifier = modifier)

        LoginBody(
            emailState = emailState,
            passwordState = passwordState,
            onSubmit = onSubmit,
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
            LoginScreen()
        }
    }
}