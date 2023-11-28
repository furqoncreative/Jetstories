package id.furqoncreative.jetstories.ui.components

import android.content.Context
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.states.PasswordState
import id.furqoncreative.jetstories.ui.components.states.TextFieldState

@Composable
fun JetstoriesPasswordTextField(
    modifier: Modifier = Modifier,
    context: Context,
    label: String,
    placeholder: String,
    passwordState: TextFieldState,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.onFocusChanged { focusState ->
            passwordState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) {
                passwordState.enableShowErrors()
            }
        },
        value = passwordState.text,
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors()
        },
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Password,
                contentDescription = stringResource(id = R.string.hide_password)
            )
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(id = R.string.hide_password)
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(id = R.string.show_password)
                    )
                }
            }
        },
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = passwordState.showErrors(),
        supportingText = {
            passwordState.getError(context)
                ?.let { error -> JetstoriesTextFieldError(textError = error) }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
        }),
    )

}

@Preview
@Composable
fun JetstoriesPasswordTextFieldPreview() {
    JetstoriesPasswordTextField(context = LocalContext.current,
        label = "Password",
        placeholder = "Enter your password",
        passwordState = rememberSaveable {
            PasswordState()
        })
}