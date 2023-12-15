package id.furqoncreative.jetstories.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.repository.FavoriteRepository
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoriteUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: UiText? = null,
    val stories: List<Story> = emptyList(),
)

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getAllFavoriteStories()
    }

    fun getAllFavoriteStories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            favoriteRepository.getAllFavorites()
                .collect { storiesResponseAsync ->
                    _uiState.update { favoriteUiState ->
                        produceFavoriteUiState(
                            storiesResponseAsync = storiesResponseAsync,
                            favoriteUiState = favoriteUiState
                        )
                    }
                }
        }
    }

    private fun produceFavoriteUiState(
        storiesResponseAsync: Async<List<Story>>,
        favoriteUiState: FavoriteUiState
    ) = when (storiesResponseAsync) {
        Async.Loading -> favoriteUiState.copy(isLoading = true, isEmpty = true)

        is Async.Error -> favoriteUiState.copy(
            isEmpty = true,
            isLoading = false,
            userMessage = UiText.DynamicString(storiesResponseAsync.errorMessage)
        )

        is Async.Success -> {
            val stories = storiesResponseAsync.data
            favoriteUiState.copy(
                isEmpty = false,
                isLoading = false,
                stories = stories
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