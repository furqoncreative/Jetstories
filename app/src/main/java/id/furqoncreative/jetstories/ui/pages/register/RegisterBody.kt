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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.EmailTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.ui.components.NameTextField
import id.furqoncreative.jetstories.ui.components.PasswordTextField
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.util.ConfirmPasswordState
import id.furqoncreative.jetstories.util.NameState
import id.furqoncreative.jetstories.util.PasswordState
import id.furqoncreative.jetstories.util.TextFieldState

@Composable
fun RegisterBody(
    modifier: Modifier = Modifier,
    emailState: TextFieldState,
    nameState: NameState,
    passwordState: PasswordState,
    confirmPasswordState: ConfirmPasswordState,
    isLoading: Boolean = false,
    onClickSignup: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        EmailTextField(emailState = emailState)

        NameTextField(nameState = nameState)

        PasswordTextField(
            label = stringResource(id = R.string.password_label),
            passwordState = passwordState,
            imeAction = ImeAction.Next
        )

        PasswordTextField(label = stringResource(id = R.string.confirmation_password_label),
            passwordState = confirmPasswordState,
            onImeAction = {
                onClickSignup()
            })

        Button(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
            enabled = emailState.isValid && nameState.isValid && passwordState.isValid && confirmPasswordState.isValid,
            onClick = {
                onClickSignup()
            }) {
            if (!isLoading) {
                Text(text = "Sign up")
            } else {
                JetstoriesProgressBar(size = 30.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBodyPreview() {
    JetStoriesTheme {
        RegisterBody(emailState = TextFieldState(),
            nameState = NameState(),
            passwordState = PasswordState(),
            confirmPasswordState = ConfirmPasswordState(PasswordState()),
            onClickSignup = {})
    }
}