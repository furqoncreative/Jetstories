package id.furqoncreative.jetstories.ui.components

import android.content.Context
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import id.furqoncreative.jetstories.utils.NameState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    context: Context,
    nameState: NameState = remember { NameState() },
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                nameState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    nameState.enableShowErrors()
                }
            },
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
        isError = nameState.showErrors(),
        supportingText = {
            nameState.getError(context)?.let { error -> TextFieldError(textError = error) }
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
    NameTextField(context = LocalContext.current)
}