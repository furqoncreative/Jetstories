package id.furqoncreative.jetstories.ui.pages.mapview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.utils.showToast

@Composable
fun MapViewScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    mapViewViewModel: MapViewViewModel = hiltViewModel()
) {
    val uiState by mapViewViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        mapViewViewModel.toastMessageShown()
    }
}