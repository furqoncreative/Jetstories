package id.furqoncreative.jetstories.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN

@Composable
fun JetstoriesApp(
    viewModel: JetstoriesViewModel
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    if (isLoggedIn) {
        JetstoriesNavGraph(
            startDestination = HOME_SCREEN
        )
    } else {
        JetstoriesNavGraph(
            startDestination = LOGIN_SCREEN
        )
    }
}