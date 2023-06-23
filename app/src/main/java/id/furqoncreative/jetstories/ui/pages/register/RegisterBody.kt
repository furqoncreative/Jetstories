package id.furqoncreative.jetstories.ui.pages.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.ui.components.EmailTextField
import id.furqoncreative.jetstories.ui.components.PasswordState
import id.furqoncreative.jetstories.ui.components.PasswordTextField
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.util.TextFieldState

@Composable
fun RegisterBody(
    emailState: TextFieldState,
    passwordState: PasswordState,
    onClickSignup: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        EmailTextField(emailState = emailState)

        PasswordTextField(passwordState = passwordState, onImeAction = {
            onClickSignup()
        })

        PasswordTextField(passwordState = passwordState, onImeAction = {
            onClickSignup()
        })

        Button(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
            enabled = emailState.isValid && passwordState.isValid,
            onClick = {
                onClickSignup()
            }) {
            Text(text = "Sign up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBodyPreview() {
    JetStoriesTheme {
        RegisterBody(emailState = TextFieldState(),
            passwordState = PasswordState(),
            onClickSignup = {})
    }
}