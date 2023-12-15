package id.furqoncreative.jetstories.ui.screens.favorite

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.fake.service.DummyStory
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class FavoriteScreenKtTest : JetstoriesScreenTest() {

    @Before
    fun setup() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController, startDestination = JetstoriesScreens.HOME_SCREEN
            )
        }
    }

    @Test
    fun showEmptyScreen_WhenFavoriteIsEmpty() = runTest {
        activity.apply {
            onNodeWithContentDescription(getString(R.string.favorite_stories))

            waitForIdle()
            onNodeWithText(getString(R.string.there_is_no_story))
        }
    }

    @Test
    fun showListOfFavorite_WhenFavoriteIsNotEmpty() = runTest {
        activity.apply {
            onNodeWithTag("story_list").performScrollToIndex(0).performClick()

            waitForIdle()
            onNodeWithContentDescription(getString(R.string.favorite_icon_button)).performClick()
            onNodeWithContentDescription(getString(R.string.back)).performClick()

            waitForIdle()
            onNodeWithContentDescription(getString(R.string.favorite_stories)).performClick()

            waitForIdle()
            onNodeWithTag("favorite_story_list").assertExists()
            onNodeWithText(DummyStory.story.name).assertIsDisplayed()
        }
    }
}