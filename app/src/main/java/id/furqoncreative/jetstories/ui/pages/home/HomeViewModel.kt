package id.furqoncreative.jetstories.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.repository.GetAllStoriesRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.util.Async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val isUserLogout: Boolean = false,
    val userMessage: String? = null,
    val stories: List<Story>? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storiesRepository: GetAllStoriesRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllStories()
    }

    private fun getAllStories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            storiesRepository.getAllStories().collect { storiesAsync ->
                _uiState.update {
                    produceHomeUiState(storiesAsync)
                }
            }
        }
    }

    private fun produceHomeUiState(storiesAsync: Async<GetAllStoriesResponse>) =
        when (storiesAsync) {
            Async.Loading -> HomeUiState(isLoading = true, isEmpty = true)

            is Async.Error -> HomeUiState(
                isEmpty = true, isLoading = false, userMessage = storiesAsync.errorMessage
            )

            is Async.Success -> {
                val stories = storiesAsync.data.listStory
                HomeUiState(
                    isEmpty = stories?.isEmpty() ?: false, isLoading = false, stories = stories
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

    fun userLogout() {
        viewModelScope.launch {
            try {
                preferencesManager.setUserToken("")
            } finally {
                val userToken = preferencesManager.getUserToken.first()
                if (userToken.isEmpty()) {
                    _uiState.update {
                        HomeUiState(isUserLogout = true, userMessage = "Logout berhasil")
                    }
                }
            }
        }
    }

}