package id.furqoncreative.jetstories.ui.screens.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ABOUT_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.ADD_STORY_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.FAVORITE_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.MAP_VIEW_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.SETTINGS_SCREEN
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class HomeScreenKtTest : JetstoriesScreenTest() {

    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController, startDestination = HOME_SCREEN
            )
        }
    }

    @Test
    fun navigateToAddStory_WhenClickedAddIconMenu() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.add_story)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(ADD_STORY_SCREEN)
        }
    }

    @Test
    fun navigateToMapViewStory_WhenClickedMapIconMenu() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.map_view)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(MAP_VIEW_SCREEN)
        }
    }

    @Test
    fun navigateToFavoriteStory_WhenClickedFavoriteIconMenu() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.favorite_stories)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(FAVORITE_SCREEN)
        }
    }

    @Test
    fun navigateToAboutScreen_WhenClickedAboutMenu() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.more_menu)).performClick()
            onNodeWithContentDescription(getString(R.string.about_page)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(ABOUT_SCREEN)
        }
    }

    @Test
    fun navigateToSettingsScreen_WhenClickedSettingsMenu() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.more_menu)).performClick()
            onNodeWithContentDescription(getString(R.string.settings)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(SETTINGS_SCREEN)
        }
    }

    @Test
    fun navigateToLoginScreen_onSuccessfulLogout() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.more_menu)).performClick()
            onNodeWithText(getString(R.string.logout)).performClick()

            waitForIdle()
            onNodeWithText(getString(R.string.yes)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(LOGIN_SCREEN)
        }
    }

    @Test
    fun showItemJetstories9_onSuccessfulScrollToIndex10() = runTest {
        activity.apply {
            onNodeWithTag("story_list").performScrollToIndex(10)

            waitForIdle()
            onNodeWithText("Jetstories 9").assertExists()
        }
    }

    @Test
    fun showEmptyScreen_WhenEnteredRandomTextOnSearchBar() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.search_placeholder)).performTextReplacement("Random")

            waitForIdle()
            onNodeWithText(getString(R.string.there_is_no_story)).assertIsDisplayed()
        }
    }
}