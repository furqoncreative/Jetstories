package id.furqoncreative.jetstories.ui.screens.detailstory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.repository.GetDetailStoryRepository
import id.furqoncreative.jetstories.model.stories.GetDetailStoryResponse
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.navigation.JetstoriesDestinationsArgs
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailStoryUiState(
    val isLoading: Boolean = false,
    val isUserLogout: Boolean = false,
    val userMessage: UiText? = null,
    val story: Story? = null,
)

@HiltViewModel
class DetailStoryViewModel @Inject constructor(
    private val detailStoryRepository: GetDetailStoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val storyId: String? = savedStateHandle[JetstoriesDestinationsArgs.STORY_ID]

    private val _uiState = MutableStateFlow(DetailStoryUiState())
    val uiState: StateFlow<DetailStoryUiState> = _uiState.asStateFlow()

    init {
        if (storyId != null) {
            getDetailStory(storyId)
        }
    }

    private fun getDetailStory(storyId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            detailStoryRepository.getDetailStory(
                id = storyId
            ).collect { storyResponseAsync ->
                _uiState.update { detailStoryUiState ->
                    produceDetailStoryUiState(
                        storyResponseAsync = storyResponseAsync,
                        detailStoryUiState = detailStoryUiState
                    )
                }
            }
        }
    }

    private fun produceDetailStoryUiState(
        storyResponseAsync: Async<GetDetailStoryResponse>,
        detailStoryUiState: DetailStoryUiState
    ) = when (storyResponseAsync) {
        Async.Loading -> detailStoryUiState.copy(isLoading = true)

        is Async.Error -> detailStoryUiState.copy(
            isLoading = false,
            userMessage = UiText.DynamicString(storyResponseAsync.errorMessage)
        )

        is Async.Success -> {
            val story = storyResponseAsync.data.story
            detailStoryUiState.copy(
                isLoading = false,
                story = story
            )
        }
    }

    fun toastMessageShown() {
        _uiState.update {
            it.copy(
                userMessage = null
            )
        }
    }
}