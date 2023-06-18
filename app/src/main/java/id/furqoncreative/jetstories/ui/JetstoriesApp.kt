package id.furqoncreative.jetstories.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph

@Composable
fun JetstoriesApp(
    viewModel: JetstoriesViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    if (isLoggedIn) {
        JetstoriesNavGraph(
            startDestination = JetstoriesDestinations.HOME_ROUTE
        )
    } else {
        JetstoriesNavGraph(
            startDestination = JetstoriesDestinations.LOGIN_ROUTE
        )
    }
}