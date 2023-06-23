package id.furqoncreative.jetstories.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.HOME_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.LOGIN_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.REGISTER_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.REGISTER_SCREEN


private object JetstoriesScreens {
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val HOME_SCREEN = "home"
}

object JetstoriesDestinations {
    const val LOGIN_ROUTE = LOGIN_SCREEN
    const val REGISTER_ROUTE = REGISTER_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
}

class JetstoriesNavigationActions(private val navHostController: NavHostController) {
    fun navigateUp() {
        navHostController.navigateUp()
    }

    fun navigateToLogin() {
        navHostController.navigate(LOGIN_ROUTE)
    }

    fun navigateToHome() {
        navHostController.navigate(HOME_ROUTE)
    }

    fun navigateToRegister() {
        navHostController.navigate(REGISTER_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}