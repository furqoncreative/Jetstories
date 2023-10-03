package id.furqoncreative.jetstories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinationsArgs.STORY_ID
import id.furqoncreative.jetstories.ui.pages.addstory.AddStoryScreen
import id.furqoncreative.jetstories.ui.pages.detailstory.DetailStoryScreen
import id.furqoncreative.jetstories.ui.pages.home.HomeScreen
import id.furqoncreative.jetstories.ui.pages.login.LoginScreen
import id.furqoncreative.jetstories.ui.pages.mapview.MapViewScreen
import id.furqoncreative.jetstories.ui.pages.register.RegisterScreen
import id.furqoncreative.jetstories.ui.pages.settings.SettingsScreen

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

        composable(JetstoriesDestinations.REGISTER_ROUTE) {
            RegisterScreen(onNavUp = {
                navAction.navigateUp()
            }, onSuccessRegister = {
                navAction.navigateUp()
            })
        }

        composable(JetstoriesDestinations.HOME_ROUTE) {
            HomeScreen(onClickAddStory = {
                navAction.navigateToAddStory()
            }, onClickSettings = {
                navAction.navigateToSettings()
            }, onUserLoggedOut = {
                navAction.navigateToLogin()
            }, onClickStory = { story ->
                navAction.navigateToDetail(story.id)
            })
        }


        composable(JetstoriesDestinations.MAP_VIEW_ROUTE) {
            MapViewScreen(
                onNavUp = {
                    navAction.navigateUp()
                }
            )
        }

        composable(JetstoriesDestinations.ADD_STORY_ROUTE) {
            AddStoryScreen(onNavUp = {
                navAction.navigateUp()
            }, onSuccessAddStory = {
                navAction.navigateToHome()
            })
        }

        composable(
            route = JetstoriesDestinations.DETAIL_ROUTE, arguments = listOf(
                navArgument(STORY_ID) { type = NavType.StringType; nullable = false },
            )
        ) {
            DetailStoryScreen(onNavUp = {
                navAction.navigateUp()
            })
        }


        composable(JetstoriesDestinations.SETTINGS_ROUTE) {
            SettingsScreen(onNavUp = {
                navAction.navigateUp()
            })
        }
    }
}