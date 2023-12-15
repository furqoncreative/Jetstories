package id.furqoncreative.jetstories.ui.screens.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
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
    fun showErrorEmailMustBeValid_whenEnteredInvalidEmail() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jet@gmail")

            waitForIdle()
            onNodeWithText(getString(R.string.email_invalid_message)).assertIsDisplayed()
        }
    }

    @Test
    fun showErrorPasswordMustBeValid_whenEnteredInvalidPassword() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("1111")

            waitForIdle()
            onNodeWithText(getString(R.string.password_invalid_message)).assertIsDisplayed()
        }
    }

    @Test
    fun showUserNotFoundSnackbar_whenEnteredUnregisteredUser() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jet@gmail.com")
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("jetstories123")
            onNodeWithText(getString(R.string.sign_in)).performClick()

            waitForIdle()
            onNodeWithText("User Not Found").assertIsDisplayed()
        }

    }

    @Test
    fun showInvalidPasswordSnackbar_whenEnteredWrongPassword() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jetstories@mail.com")
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("jetstories")
            onNodeWithText(getString(R.string.sign_in)).performClick()

            waitForIdle()
            onNodeWithText("Invalid Password").assertIsDisplayed()
        }
    }

    @Test
    fun navigateToRegisterScreen_onClickSignup() = runTest {
        onNodeWithText(activity.getString(R.string.sign_up)).performClick()

        waitForIdle()
        navHostController.assertCurrentRouteName(REGISTER_SCREEN)
    }

    @Test
    fun navigateToHomeScreen_onSuccessfulLogin() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jetstories@mail.com")
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("jetstories123")
            onNodeWithText(getString(R.string.sign_in)).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(HOME_SCREEN)
        }
    }
}