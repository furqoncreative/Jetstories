package id.furqoncreative.jetstories.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinationsArgs.STORY_ID
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ABOUT_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ADD_STORY_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.DETAIL_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.FAVORITE_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.MAP_VIEW_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.REGISTER_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.SETTINGS_SCREEN


object JetstoriesScreens {
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail"
    const val MAP_VIEW_SCREEN = "map-view"
    const val ADD_STORY_SCREEN = "add-story"
    const val SETTINGS_SCREEN = "settings"
    const val ABOUT_SCREEN = "about"
    const val FAVORITE_SCREEN = "favorite"
}

object JetstoriesDestinationsArgs {
    const val STORY_ID = "storyId"
}

object JetstoriesDestinations {
    const val DETAIL_ROUTE = "$DETAIL_SCREEN/{$STORY_ID}"
}

class JetstoriesNavigationActions(private val navHostController: NavHostController) {
    fun navigateUp() {
        navHostController.navigateUp()
    }

    fun navigateToLogin() {
        navHostController.navigate(LOGIN_SCREEN)
    }

    fun navigateToRegister() {
        navHostController.navigate(REGISTER_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToHome() {
        navHostController.navigate(HOME_SCREEN) {
            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
        }
        navHostController.graph.setStartDestination(HOME_SCREEN)
    }

    fun navigateToDetail(storyId: String) {
        navHostController.navigate("$DETAIL_SCREEN/$storyId")
    }

    fun navigateToMapView() {
        navHostController.navigate(MAP_VIEW_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToAddStory() {
        navHostController.navigate(ADD_STORY_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToSettings() {
        navHostController.navigate(SETTINGS_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToAbout() {
        navHostController.navigate(ABOUT_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateToFavorite() {
        navHostController.navigate(FAVORITE_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}