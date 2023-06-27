package id.furqoncreative.jetstories.ui.components

import android.content.Context
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.utils.EmailState
import id.furqoncreative.jetstories.utils.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetstoriesEmailTextField(
    modifier: Modifier = Modifier,
    context: Context,
    emailState: TextFieldState = remember { EmailState() },
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier.onFocusChanged { focusState ->
            emailState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) {
                emailState.enableShowErrors()
            }
        },
        value = emailState.text,
        onValueChange = {
            emailState.text = it
            emailState.enableShowErrors()
        },
        label = {
            Text(text = stringResource(R.string.email_label))
        },
        placeholder = {
            Text(text = stringResource(R.string.email_placeholder))
        },
        isError = emailState.showErrors(),
        supportingText = {
            emailState.getError(context)
                ?.let { error -> JetstoriesTextFieldError(textError = error) }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(id = R.string.hide_password)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
        }),
    )
}

@Preview(showBackground = true)
@Composable
fun JetstoriesEmailTextFieldPreview() {
    JetstoriesEmailTextField(context = LocalContext.current)
}