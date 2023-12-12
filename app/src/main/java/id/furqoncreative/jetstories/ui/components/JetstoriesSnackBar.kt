package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JetstoriesSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.fillMaxWidth()
    )
}

suspend fun showSnackBar(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Indefinite,
    onActionClick: () -> Unit = {},
    onDismissedClick: () -> Unit = {},
    withDismissAction: Boolean = false
) {
    val result = snackbarHostState
        .showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = snackbarDuration,
            withDismissAction = withDismissAction
        )
    when (result) {
        SnackbarResult.ActionPerformed -> {
            onActionClick()
        }

        SnackbarResult.Dismissed -> {
            onDismissedClick()
        }

        else -> {}
    }
}