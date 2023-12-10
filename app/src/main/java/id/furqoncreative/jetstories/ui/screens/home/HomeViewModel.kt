package id.furqoncreative.jetstories.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.GetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val isUserLogout: Boolean = false,
    val userMessage: UiText? = null,
    val searchQuery: String = "",
    val stories: Flow<PagingData<Story>> = emptyFlow(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storiesRepository: GetAllStoriesWithPaginationRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getStories(uiState.value.searchQuery)
    }

    private fun getStories(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            if (query.isNotEmpty()) {
                storiesRepository.searchStories(query = query)
                    .collect { storiesResponseAsync ->
                        _uiState.update { homeUiState ->
                            produceHomeUiState(
                                storiesResponseAsync = storiesResponseAsync,
                                homeUiState = homeUiState
                            )
                        }
                    }
            } else {
                storiesRepository.getStoriesWithPagination()
                    .collect { storiesResponseAsync ->
                        _uiState.update { homeUiState ->
                            produceHomeUiState(
                                storiesResponseAsync = storiesResponseAsync,
                                homeUiState = homeUiState
                            )
                        }
                    }
            }
        }
    }

    private fun produceHomeUiState(
        storiesResponseAsync: Async<PagingData<Story>>,
        homeUiState: HomeUiState
    ) = when (storiesResponseAsync) {
        Async.Loading -> homeUiState.copy(isLoading = true, isEmpty = true)

        is Async.Error -> homeUiState.copy(
            isEmpty = true,
            isLoading = false,
            userMessage = UiText.DynamicString(storiesResponseAsync.errorMessage)
        )

        is Async.Success -> {
            val stories = storiesResponseAsync.data
            homeUiState.copy(
                isEmpty = false,
                isLoading = false,
                stories = flowOf(stories).cachedIn(viewModelScope)
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
                        HomeUiState(
                            isUserLogout = true,
                            userMessage = UiText.StringResource(R.string.logged_out)
                        )
                    }
                }
            }
        }
    }

    fun searchStory(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
        getStories(query)
    }
}