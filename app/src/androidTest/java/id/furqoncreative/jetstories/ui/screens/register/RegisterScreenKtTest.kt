package id.furqoncreative.jetstories.ui.screens.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
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
    fun showEmailErrorSupportingText_whenEnteredInvalidEmail() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.email_label)).performTextReplacement("jet@gmail")

            waitForIdle()
            onNodeWithText(getString(R.string.email_invalid_message)).assertIsDisplayed()
        }
    }

    @Test
    fun showPasswordErrorPasswordDoesntMatch_whenEnteredDifferentPassword() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.password_label)).performTextReplacement("1111")
            onNodeWithText(getString(R.string.confirmation_password_label)).performTextReplacement("2222")

            waitForIdle()
            onNodeWithText(getString(R.string.confirm_password_invalid_message)).assertIsDisplayed()
        }
    }

}