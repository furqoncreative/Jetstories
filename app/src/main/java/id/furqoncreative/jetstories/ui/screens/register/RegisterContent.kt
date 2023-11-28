package id.furqoncreative.jetstories.ui.screens.register

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesEmailTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesNameTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesPasswordTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.ConfirmPasswordState
import id.furqoncreative.jetstories.utils.NameState
import id.furqoncreative.jetstories.utils.PasswordState
import id.furqoncreative.jetstories.utils.TextFieldState

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    context: Context,
    emailState: TextFieldState,
    nameState: NameState,
    passwordState: PasswordState,
    confirmPasswordState: ConfirmPasswordState,
    isLoading: Boolean = false,
    onClickSignup: () -> Unit,
) {
    val commonModifier = modifier.fillMaxWidth()

    Column(
        modifier = commonModifier
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        JetstoriesEmailTextField(
            modifier = commonModifier, context = context, emailState = emailState
        )

        JetstoriesNameTextField(modifier = commonModifier, context = context, nameState = nameState)

        JetstoriesPasswordTextField(
            modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder),
            passwordState = passwordState,
            imeAction = ImeAction.Next
        )

        JetstoriesPasswordTextField(modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.confirmation_password_label),
            placeholder = stringResource(id = R.string.confirm_password_placeholder),
            passwordState = confirmPasswordState,
            onImeAction = {
                onClickSignup()
            })

        Button(modifier = commonModifier.height(56.dp),
            enabled = emailState.isValid && nameState.isValid && passwordState.isValid && confirmPasswordState.isValid,
            onClick = {
                onClickSignup()
            }) {
            if (!isLoading) {
                Text(text = stringResource(R.string.sign_up))
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
        RegisterContent(context = LocalContext.current,
            emailState = TextFieldState(),
            nameState = NameState(),
            passwordState = PasswordState(),
            confirmPasswordState = ConfirmPasswordState(PasswordState()),
            onClickSignup = {})
    }
}