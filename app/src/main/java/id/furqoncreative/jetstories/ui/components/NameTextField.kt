package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    nameState: NameState = remember { NameState() }, onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = nameState.text,
        onValueChange = {
            nameState.text = it
            nameState.enableShowErrors()
        },
        label = {
            Text(text = stringResource(R.string.name_label))
        },
        placeholder = {
            Text(text = stringResource(R.string.name_placeholder))
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                nameState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    nameState.enableShowErrors()
                }
            },
        isError = nameState.showErrors(),
        supportingText = {
            nameState.getError()?.let { error -> TextFieldError(textError = error) }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
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
fun NameTextFieldPreview() {
    NameTextField()
}