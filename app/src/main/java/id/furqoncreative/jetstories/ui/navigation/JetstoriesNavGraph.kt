package id.furqoncreative.jetstories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.furqoncreative.jetstories.ui.pages.login.LoginScreen

@Composable
fun JetstoriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = JetstoriesDestinations.LOGIN_ROUTE,
    navAction: JetstoriesNavigationActions = remember(navController) {
        JetstoriesNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(JetstoriesDestinations.LOGIN_ROUTE) {
            LoginScreen()
        }
    }
}