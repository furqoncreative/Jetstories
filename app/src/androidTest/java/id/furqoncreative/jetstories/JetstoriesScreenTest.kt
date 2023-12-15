package id.furqoncreative.jetstories

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import org.junit.Rule

@Suppress("LeakingThis")
abstract class JetstoriesScreenTest {
    @JvmField
    @Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @JvmField
    @Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<JetstoriesActivity>()

    lateinit var navHostController: TestNavHostController

    fun inject() = hiltRule.inject()

    fun setJetstoriesContent(content: @Composable () -> Unit) =
        composeTestRule.activity.setContent {
            JetStoriesTheme {
                navHostController = TestNavHostController(LocalContext.current)
                navHostController.navigatorProvider.addNavigator(ComposeNavigator())
                content()
            }
        }

    fun runTest(
        body: AndroidComposeTestRule<ActivityScenarioRule<JetstoriesActivity>, JetstoriesActivity>.() -> Unit
    ) = composeTestRule.run(body)
}