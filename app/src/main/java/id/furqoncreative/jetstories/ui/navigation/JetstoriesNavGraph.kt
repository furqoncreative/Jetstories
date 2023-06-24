package id.furqoncreative.jetstories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.furqoncreative.jetstories.ui.pages.home.HomeScreen
import id.furqoncreative.jetstories.ui.pages.login.LoginScreen
import id.furqoncreative.jetstories.ui.pages.register.RegisterScreen

@Composable
fun JetstoriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = JetstoriesDestinations.LOGIN_ROUTE,
    navAction: JetstoriesNavigationActions = remember(navController) {
        JetstoriesNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(JetstoriesDestinations.LOGIN_ROUTE) {
            LoginScreen(onSuccessLogin = {
                navAction.navigateToHome()
            }, onClickSignup = {
                navAction.navigateToRegister()
            })
        }

        composable(JetstoriesDestinations.HOME_ROUTE) {
            HomeScreen(onClickAddStory = {},
                onClickStoryItem = {},
                onClickSettings = {},
                onUserLoggedOut = {
                    navAction.navigateToLogin()
                })
        }

        composable(JetstoriesDestinations.REGISTER_ROUTE) {
            RegisterScreen(onNavUp = {
                navAction.navigateUp()
            }, onSuccessRegister = {
                navAction.navigateUp()
            })
        }
    }
}