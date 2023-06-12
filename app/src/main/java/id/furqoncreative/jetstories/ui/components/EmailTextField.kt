package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.util.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    emailState: TextFieldState = remember { EmailState() }, onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = emailState.text,
        label = {
            Text(text = stringResource(R.string.email_label))
        },
        placeholder = {
            Text(text = stringResource(R.string.email_placeholder))
        },
        onValueChange = {

        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
        }),
    )

    emailState.getError()?.let { error -> TextFieldError(textError = error) }

}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview() {
    EmailTextField()
}