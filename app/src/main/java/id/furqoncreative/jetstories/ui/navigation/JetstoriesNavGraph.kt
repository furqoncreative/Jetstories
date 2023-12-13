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
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ABOUT_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ADD_STORY_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.FAVORITE_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.MAP_VIEW_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.REGISTER_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.SETTINGS_SCREEN
import id.furqoncreative.jetstories.ui.screens.about.AboutScreen
import id.furqoncreative.jetstories.ui.screens.addstory.AddStoryScreen
import id.furqoncreative.jetstories.ui.screens.addstory.AddStoryViewModel
import id.furqoncreative.jetstories.ui.screens.detailstory.DetailStoryScreen
import id.furqoncreative.jetstories.ui.screens.detailstory.DetailStoryViewModel
import id.furqoncreative.jetstories.ui.screens.favorite.FavoriteScreen
import id.furqoncreative.jetstories.ui.screens.favorite.FavoriteViewModel
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
    startDestination: String = LOGIN_SCREEN,
    navAction: JetstoriesNavigationActions = remember(navController) {
        JetstoriesNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(LOGIN_SCREEN) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                onNavigateToHome = {
                    navAction.navigateToHome()
                },
                onNavigateToRegister = {
                    navAction.navigateToRegister()
                },
                loginViewModel = loginViewModel
            )
        }

        composable(REGISTER_SCREEN) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                onNavUp = {
                    navAction.navigateUp()
                },
                onSuccessRegister = {
                    navAction.navigateToLogin()
                },
                registerViewModel = registerViewModel
            )
        }

        composable(HOME_SCREEN) {
            val homeViewViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                onNavigateToAddStory = {
                    navAction.navigateToAddStory()
                },
                onNavigateToMapView = {
                    navAction.navigateToMapView()
                },
                onNavigateToSettings = {
                    navAction.navigateToSettings()
                },
                onNavigateToAbout = {
                    navAction.navigateToAbout()
                },
                onUserLoggedOut = {
                    navAction.navigateToLogin()
                },
                onNavigateToDetail = { story ->
                    story?.let { navAction.navigateToDetail(it.id) }
                },
                onNavigateToFavorite = {
                    navAction.navigateToFavorite()
                },
                homeViewModel = homeViewViewModel
            )
        }


        composable(MAP_VIEW_SCREEN) {
            val mapViewModel = hiltViewModel<MapViewViewModel>()
            MapViewScreen(
                onNavUp = {
                    navAction.navigateUp()
                },
                onNavigateToDetail = { story ->
                    navAction.navigateToDetail(story.id)
                },
                mapViewViewModel = mapViewModel
            )
        }

        composable(ADD_STORY_SCREEN) {
            val addStoryViewModel = hiltViewModel<AddStoryViewModel>()
            AddStoryScreen(
                onNavUp = {
                    navAction.navigateUp()
                },
                onSuccessAddStory = {
                    navAction.navigateToHome()
                },
                addStoryViewModel = addStoryViewModel
            )
        }

        composable(
            route = JetstoriesDestinations.DETAIL_ROUTE, arguments = listOf(
                navArgument(STORY_ID) { type = NavType.StringType; nullable = false },
            )
        ) {
            val detailStoryViewViewModel = hiltViewModel<DetailStoryViewModel>()
            DetailStoryScreen(
                onNavUp = {
                    navAction.navigateUp()
                },
                detailStoryViewModel = detailStoryViewViewModel
            )
        }


        composable(SETTINGS_SCREEN) {
            val settingViewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                onNavUp = {
                    navAction.navigateUp()
                },
                settingsViewModel = settingViewModel
            )
        }

        composable(ABOUT_SCREEN) {
            AboutScreen(
                onNavUp = {
                    navAction.navigateUp()
                }
            )
        }

        composable(FAVORITE_SCREEN) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(
                favoriteViewModel = favoriteViewModel,
                onNavUp = {
                    navAction.navigateUp()
                },
                onNavigateToDetail = { story ->
                    story?.let { it1 -> navAction.navigateToDetail(it1.id) }
                }
            )
        }
    }
}