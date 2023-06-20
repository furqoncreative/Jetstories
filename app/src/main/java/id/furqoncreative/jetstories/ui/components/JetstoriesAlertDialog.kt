package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun JetstoriesAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String? = null,
    confirmText: String,
    confirmAction: () -> Unit,
    dismissText: String,
    openDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    if (openDialog.value) {
        AlertDialog(modifier = modifier, onDismissRequest = {
            openDialog.value = false
        }, icon = { Icon(Icons.Filled.Logout, contentDescription = null) }, title = {
            Text(text = title)
        }, text = {
            if (!text.isNullOrEmpty()) {
                Text(modifier = Modifier.fillMaxWidth(), text = text, textAlign = TextAlign.Center)
            }
        }, confirmButton = {
            TextButton(onClick = {
                confirmAction()
                openDialog.value = false
            }) {
                Text(text = confirmText)
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text(text = dismissText)
            }
        })
    }
}