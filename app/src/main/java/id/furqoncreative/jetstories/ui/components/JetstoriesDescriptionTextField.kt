package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import id.furqoncreative.jetstories.utils.DescriptionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetstoriesDescriptionTextField(
    modifier: Modifier = Modifier,
    descriptionState: DescriptionState,
    onImeAction: () -> Unit = {}
) {
    TextField(modifier = modifier
        .fillMaxWidth()
        .onFocusChanged { focusState ->
            descriptionState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) {
                descriptionState.enableShowErrors()
            }
        }, value = descriptionState.text, onValueChange = {
        descriptionState.text = it
        descriptionState.enableShowErrors()

    }, label = {
        Text(text = "Description")
    }, placeholder = {
        Text("Tell your story...")
    }, isError = descriptionState.showErrors(), supportingText = {
        descriptionState.getError()?.let { error -> TextFieldError(textError = error) }
    }, trailingIcon = {
        IconButton(onClick = { descriptionState.text = "" }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear")
        }
    }, keyboardActions = KeyboardActions(onDone = {
        onImeAction()
    }), maxLines = 3
    )

}