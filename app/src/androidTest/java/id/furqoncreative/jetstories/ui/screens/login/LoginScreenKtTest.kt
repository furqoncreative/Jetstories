package id.furqoncreative.jetstories.ui.screens.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.LOGIN_SCREEN
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.REGISTER_SCREEN
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
    fun showUserNotFoundSnackbar_whenEnteredUnregisteredUser() = runTest {
        onNodeWithText("Email").performTextReplacement("jet@gmail.com")
        onNodeWithText("Password").performTextReplacement("jetstories123")
        onNodeWithText("Sign in").performClick()

        waitForIdle()
        onNodeWithText("User Not Found").assertIsDisplayed()
    }

    @Test
    fun showInvalidPasswordSnackbar_whenEnteredWrongPassword() = runTest {
        onNodeWithText("Email").performTextReplacement("jetstories@mail.com")
        onNodeWithText("Password").performTextReplacement("jetstories")
        onNodeWithText("Sign in").performClick()

        waitForIdle()
        onNodeWithText("Invalid Password").assertIsDisplayed()
    }

    @Test
    fun navigateToRegisterScreen_onClickSignup() = runTest {
        onNodeWithText("Sign up").performClick()

        waitForIdle()
        navHostController.assertCurrentRouteName(REGISTER_SCREEN)
    }

    @Test
    fun navigateToHomeScreen_onSuccessfulLogin() = runTest {
        onNodeWithText("Email").performTextReplacement("jetstories@mail.com")
        onNodeWithText("Password").performTextReplacement("jetstories123")
        onNodeWithText("Sign in").performClick()

        waitForIdle()
        navHostController.assertCurrentRouteName(HOME_SCREEN)
    }
}