package id.furqoncreative.jetstories.ui.pages.addstory

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.utils.DescriptionState


data class AddStoryUiState(
    val descriptionState: DescriptionState = DescriptionState(),
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val isSuccessAddStory: Boolean = false
)

@HiltViewModel
class AddStoryViewModel : ViewModel()