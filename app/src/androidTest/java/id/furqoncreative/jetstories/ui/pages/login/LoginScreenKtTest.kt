package id.furqoncreative.jetstories.ui.pages.login

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesActivity
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginScreenKtTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<JetstoriesActivity>()

    private lateinit var navHostController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            JetStoriesTheme {
                navHostController = TestNavHostController(LocalContext.current)
                navHostController.navigatorProvider.addNavigator(ComposeNavigator())
                JetstoriesNavGraph(navController = navHostController)
            }
        }
    }

    @Test
    fun navigateToHomeScreen_onSuccessfulLogin() {
        composeTestRule.apply {
            onNodeWithText("Email").performTextReplacement("jetstories@mail.com")
            onNodeWithText("Password").performTextReplacement("jetstories123")
            onNodeWithText("Sign in").performClick()
            waitForIdle()
            navHostController.assertCurrentRouteName(JetstoriesScreens.HOME_SCREEN)
        }
    }
}