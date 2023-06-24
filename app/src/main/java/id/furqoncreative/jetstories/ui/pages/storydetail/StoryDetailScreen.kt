package id.furqoncreative.jetstories.ui.pages.storydetail

import androidx.compose.runtime.Composable
import id.furqoncreative.jetstories.model.stories.Story

data class StoryDetailUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val isUserLogout: Boolean = false,
    val userMessage: String? = null,
    val stories: Story? = null,
)
@Composable
fun StoryDetailScreen() {
    
}