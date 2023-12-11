package id.furqoncreative.jetstories.ui.screens.home

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class HomeScreenKtTest : JetstoriesScreenTest() {

    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController,
                startDestination = HOME_SCREEN
            )
        }
    }

    @Test
    fun navigateToLoginScreen_onSuccessfulLogout() = runTest {
        onNodeWithContentDescription("More Menu").performClick()
        onNodeWithText("Logout").performClick()

        waitForIdle()
        onNodeWithText("Yes").performClick()

        waitForIdle()
        navHostController.assertCurrentRouteName(JetstoriesScreens.LOGIN_SCREEN)
    }
}