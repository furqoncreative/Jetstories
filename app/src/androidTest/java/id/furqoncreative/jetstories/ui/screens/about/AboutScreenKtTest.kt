package id.furqoncreative.jetstories.ui.screens.about

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class AboutScreenKtTest : JetstoriesScreenTest() {
    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController, startDestination = JetstoriesScreens.ABOUT_SCREEN
            )
        }
    }

    @Test
    fun showPersonalInfo_WhenLoadAboutScreen() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.about_name)).assertIsDisplayed()
            onNodeWithText(getString(R.string.about_email)).assertIsDisplayed()
            onNodeWithContentDescription(getString(R.string.profile_picture)).assertIsDisplayed()
        }
    }
}