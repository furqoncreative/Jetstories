package id.furqoncreative.jetstories.ui.components

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.utils.DescriptionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetstoriesDescriptionTextField(
    modifier: Modifier = Modifier,
    context: Context,
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
        Text(text = stringResource(R.string.description_label))
    }, placeholder = {
        Text(stringResource(R.string.description_placeholder))
    }, isError = descriptionState.showErrors(), supportingText = {
        descriptionState.getError(context)?.let { error -> JetstoriesTextFieldError(textError = error) }
    }, trailingIcon = {
        IconButton(onClick = { descriptionState.text = "" }) {
            Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.clear_text))
        }
    }, keyboardActions = KeyboardActions(onDone = {
        onImeAction()
    }), maxLines = 3
    )

}