package id.furqoncreative.jetstories.ui.screens.detailstory

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import dagger.hilt.android.testing.HiltAndroidTest
import id.furqoncreative.jetstories.JetstoriesScreenTest
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.assertCurrentRouteName
import id.furqoncreative.jetstories.fake.service.DummyStory
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinations.DETAIL_ROUTE
import id.furqoncreative.jetstories.ui.navigation.JetstoriesNavGraph
import id.furqoncreative.jetstories.ui.navigation.JetstoriesScreens.HOME_SCREEN
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class DetailStoryScreenKtTest : JetstoriesScreenTest() {

    @Before
    fun setup() {
        inject()
        setJetstoriesContent {
            JetstoriesNavGraph(
                navController = navHostController, startDestination = HOME_SCREEN

            )
        }
    }

    @Test
    fun showDetailStory_OnClickedItemStory() = runTest {
        val story = DummyStory.story
        activity.apply {
            onNodeWithTag("story_list").performScrollToIndex(0).performClick()

            waitForIdle()
            navHostController.assertCurrentRouteName(DETAIL_ROUTE)

            onNodeWithText(
                getString(
                    R.string.detail_story,
                    story.name
                )
            ).assertIsDisplayed()
            onNodeWithText(story.description).assertIsDisplayed()
        }
    }
}