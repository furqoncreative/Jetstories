package id.furqoncreative.jetstories.ui.screens.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import org.junit.Before
import org.junit.Test


@HiltAndroidTest
class RegisterScreenKtTest : JetstoriesScreenTest() {
    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController,
                startDestination = JetstoriesScreens.REGISTER_SCREEN
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
    fun showErrorPasswordDoesntMatch_whenEnteredDifferentPassword() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("1111")
            onNodeWithText(getString(R.string.confirmation_password_label)).performTextReplacement("2222")

            waitForIdle()
            onNodeWithText(getString(R.string.confirm_password_invalid_message)).assertIsDisplayed()
        }
    }

    @Test
    fun showErrorNameMustBeNotEmpty_whenEnteredBlankName() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.name_label)).performTextReplacement("")
            onNodeWithText(getString(R.string.name_label)).performImeAction()

            waitForIdle()
            onNodeWithText(getString(R.string.name_invalid_message)).assertIsDisplayed()
        }
    }

    @Test
    fun showErrorEmailIsAlreadyTaken_whenEnteredUsedEmail() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jetstories@mail.com")
            onNodeWithText(getString(R.string.name_label)).performTextReplacement("Test")
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("12345678")
            onNodeWithText(getString(R.string.confirmation_password_label)).performTextReplacement("12345678")
            onNodeWithText(getString(R.string.sign_up)).performClick()

            waitForIdle()
            onNodeWithText("Email is already taken").assertIsDisplayed()
        }
    }
}