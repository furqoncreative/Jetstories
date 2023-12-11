package id.furqoncreative.jetstories.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesActivity
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenKtTest {
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
                JetstoriesNavGraph(
                    navController = navHostController,
                    startDestination = HOME_SCREEN
                )
            }
        }
    }

    @Test
    fun navigateToLoginScreen_onSuccessfulLogout() {

        composeTestRule.apply {
            onNodeWithContentDescription("More Menu").performClick()
            onNodeWithText("Logout").performClick()
            waitForIdle()
            onNodeWithText("Yes").performClick()
            waitForIdle()
            navHostController.assertCurrentRouteName(JetstoriesScreens.LOGIN_SCREEN)
        }
    }
}