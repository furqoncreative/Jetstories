package id.furqoncreative.jetstories.ui.screens.login

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class LoginScreenKtTest : JetstoriesScreenTest() {
    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController,
                startDestination = LOGIN_SCREEN
            )
        }
    }

    @Test
    fun navigateToHomeScreen_onSuccessfulLogin() = runTest {
        onNodeWithText("Email").performTextReplacement("jetstories@mail.com")
        onNodeWithText("Password").performTextReplacement("jetstories123")
        onNodeWithText("Sign in").performClick()

        waitForIdle()
        navHostController.assertCurrentRouteName(JetstoriesScreens.HOME_SCREEN)
    }
}