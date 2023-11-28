package id.furqoncreative.jetstories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinationsArgs.STORY_ID
import id.furqoncreative.jetstories.ui.screens.addstory.AddStoryScreen
import id.furqoncreative.jetstories.ui.screens.addstory.AddStoryViewModel
import id.furqoncreative.jetstories.ui.screens.detailstory.DetailStoryScreen
import id.furqoncreative.jetstories.ui.screens.detailstory.DetailStoryViewModel
import id.furqoncreative.jetstories.ui.screens.home.HomeScreen
import id.furqoncreative.jetstories.ui.screens.home.HomeViewModel
import id.furqoncreative.jetstories.ui.screens.login.LoginScreen
import id.furqoncreative.jetstories.ui.screens.login.LoginViewModel
import id.furqoncreative.jetstories.ui.screens.mapview.MapViewScreen
import id.furqoncreative.jetstories.ui.screens.mapview.MapViewViewModel
import id.furqoncreative.jetstories.ui.screens.register.RegisterScreen
import id.furqoncreative.jetstories.ui.screens.register.RegisterViewModel
import id.furqoncreative.jetstories.ui.screens.settings.SettingsScreen
import id.furqoncreative.jetstories.ui.screens.settings.SettingsViewModel

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
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(onSuccessLogin = {
                navAction.navigateToHome()
            }, onClickSignup = {
                navAction.navigateToRegister()
            }, loginViewModel = loginViewModel)
        }

        composable(JetstoriesDestinations.REGISTER_ROUTE) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(onNavUp = {
                navAction.navigateUp()
            }, onSuccessRegister = {
                navAction.navigateUp()
            },
                registerViewModel = registerViewModel
            )
        }

        composable(JetstoriesDestinations.HOME_ROUTE) {
            val homeViewViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(onClickAddStory = {
                navAction.navigateToAddStory()
            }, onClickMapView = {
                navAction.navigateToMapView()
            }, onClickSettings = {
                navAction.navigateToSettings()
            }, onUserLoggedOut = {
                navAction.navigateToLogin()
            }, onClickStory = { story ->
                story?.let { navAction.navigateToDetail(it.id) }
            }, homeViewModel = homeViewViewModel)
        }


        composable(JetstoriesDestinations.MAP_VIEW_ROUTE) {
            val mapViewModel = hiltViewModel<MapViewViewModel>()
            MapViewScreen(onNavUp = {
                navAction.navigateUp()
            }, onClickStory = { story ->
                navAction.navigateToDetail(story.id)
            },
                mapViewViewModel = mapViewModel
            )
        }

        composable(JetstoriesDestinations.ADD_STORY_ROUTE) {
            val addStoryViewModel = hiltViewModel<AddStoryViewModel>()
            AddStoryScreen(onNavUp = {
                navAction.navigateUp()
            }, onSuccessAddStory = {
                navAction.navigateToHome()
            }, addStoryViewModel = addStoryViewModel)
        }

        composable(
            route = JetstoriesDestinations.DETAIL_ROUTE, arguments = listOf(
                navArgument(STORY_ID) { type = NavType.StringType; nullable = false },
            )
        ) {
            val detailStoryViewViewModel = hiltViewModel<DetailStoryViewModel>()
            DetailStoryScreen(onNavUp = {
                navAction.navigateUp()
            }, detailStoryViewModel = detailStoryViewViewModel)
        }


        composable(JetstoriesDestinations.SETTINGS_ROUTE) {
            val settingViewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(onNavUp = {
                navAction.navigateUp()
            }, settingsViewModel = settingViewModel)
        }
    }
}