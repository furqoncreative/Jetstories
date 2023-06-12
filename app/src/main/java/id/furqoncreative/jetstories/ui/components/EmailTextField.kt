package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.util.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    emailState: TextFieldState = remember { EmailState() }, onImeAction: () -> Unit = {}
) {

    OutlinedTextField(value = "", label = {
        Text(text = stringResource(R.string.email_label))
    }, placeholder = {
        Text(text = stringResource(R.string.email_placeholder))
    }, onValueChange = {

    }, modifier = Modifier.fillMaxWidth()
    )

}