package id.furqoncreative.jetstories.ui.screens.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SettingsScreenKtTest : JetstoriesScreenTest() {
    @Before
    fun setUp() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController,
                startDestination = JetstoriesScreens.SETTINGS_SCREEN
            )
        }
    }

    @Test
    fun changeAppLanguageToBahasaIndonesia() = runTest {
        activity.apply {
            onNodeWithText(getString(R.string.language)).performClick()

            waitForIdle()
            onNodeWithText("Bahasa Indonesia").performClick()
            onNodeWithText(getString(R.string.confirm)).performClick()

            runOnUiThread {
                recreate()
            }
        }

        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController,
                startDestination = JetstoriesScreens.SETTINGS_SCREEN
            )
        }

        onNodeWithText("Pengaturan").assertIsDisplayed()
    }
}