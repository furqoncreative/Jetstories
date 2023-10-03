package id.furqoncreative.jetstories.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.ADD_STORY_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.HOME_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.LOGIN_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.MAP_VIEW_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.REGISTER_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.SETTINGS_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinationsArgs.STORY_ID
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ADD_STORY_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.DETAIL_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.MAP_VIEW_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.REGISTER_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.SETTINGS_SCREEN


private object JetstoriesScreens {
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail"
    const val MAP_VIEW_SCREEN = "map-view"
    const val ADD_STORY_SCREEN = "add-story"
    const val SETTINGS_SCREEN = "settings"
}

object JetstoriesDestinationsArgs {
    const val STORY_ID = "storyId"
}

object JetstoriesDestinations {
    const val LOGIN_ROUTE = LOGIN_SCREEN
    const val REGISTER_ROUTE = REGISTER_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
    const val ADD_STORY_ROUTE = ADD_STORY_SCREEN
    const val MAP_VIEW_ROUTE = MAP_VIEW_SCREEN
    const val DETAIL_ROUTE = "$DETAIL_SCREEN/{$STORY_ID}"
    const val SETTINGS_ROUTE = SETTINGS_SCREEN
}

class JetstoriesNavigationActions(private val navHostController: NavHostController) {
    fun navigateUp() {
        navHostController.navigateUp()
    }

    fun navigateToLogin() {
        navHostController.navigate(LOGIN_ROUTE)
    }

    fun navigateToRegister() {
        navHostController.navigate(REGISTER_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToHome() {
        navHostController.navigate(HOME_ROUTE) {
            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
        }
        navHostController.graph.setStartDestination(HOME_ROUTE)
    }

    fun navigateToDetail(storyId: String) {
        navHostController.navigate("$DETAIL_SCREEN/$storyId") {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToMapView() {
        navHostController.navigate(MAP_VIEW_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToAddStory() {
        navHostController.navigate(ADD_STORY_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToSettings() {
        navHostController.navigate(SETTINGS_ROUTE) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}