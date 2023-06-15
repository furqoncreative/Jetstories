package id.furqoncreative.jetstories.ui.navigation

import androidx.navigation.NavHostController
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.LOGIN_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN


private object JetstoriesScreens {
    const val LOGIN_SCREEN = "login"
}

object JetstoriesDestinationsArgs {
    const val USER_DATA = "userData"
}


object JetstoriesDestinations {
    const val LOGIN_ROUTE = LOGIN_SCREEN
}

class JetstoriesNavigationActions(private val navHostController: NavHostController) {
    fun navigateToLogin(){
        navHostController.navigate(LOGIN_ROUTE)
    }
}