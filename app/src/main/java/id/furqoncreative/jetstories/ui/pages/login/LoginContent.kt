package id.furqoncreative.jetstories.ui.pages.login

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesEmailTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesPasswordTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.PasswordState
import id.furqoncreative.jetstories.utils.TextFieldState

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    context: Context,
    emailState: TextFieldState,
    passwordState: PasswordState,
    onSubmit: () -> Unit,
    onClickSignup: () -> Unit,
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

        JetstoriesPasswordTextField(modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder),
            passwordState = passwordState,
            onImeAction = {
                onSubmit()
            })

        Row(
            modifier = commonModifier.align(Alignment.End),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = stringResource(R.string.dont_have_an_account)
            )
            Text(text = stringResource(id = R.string.sign_up),
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onClickSignup()
                    })
        }

        Button(modifier = commonModifier.height(56.dp),
            enabled = emailState.isValid && passwordState.isValid,
            onClick = {
                onSubmit()
            }) {
            if (!isLoading) {
                Text(text = stringResource(id = R.string.sign_in))
            } else {
                JetstoriesProgressBar(size = 30.dp)
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