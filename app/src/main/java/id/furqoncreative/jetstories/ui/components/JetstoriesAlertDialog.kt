package id.furqoncreative.jetstories.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun JetstoriesAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable (() -> Unit)? = null,
    confirmText: String,
    confirmAction: () -> Unit,
    dismissText: String,
    openDialog: MutableState<Boolean>
) {
    if (openDialog.value) {
        AlertDialog(modifier = modifier, onDismissRequest = {
            openDialog.value = false
        }, icon = icon, title = {
            Text(text = title)
        }, text = content, confirmButton = {
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