package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.furqoncreative.jetstories.util.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    emailState: TextFieldState = remember { PasswordState() }, onImeAction: () -> Unit = {}
) {
    OutlinedTextField(value = "", label = {
        Text(text = "Password")
    }, placeholder = {
        Text(text = "Masukan password Anda")
    }, onValueChange = {

    }, modifier = Modifier.fillMaxWidth()
    )

}

@Preview
@Composable
fun PasswordTextFieldPreview() {

}