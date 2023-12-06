package id.furqoncreative.jetstories.ui.screens.mapview

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.repository.GetAllStoriesRepository
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapViewUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val isUserLogout: Boolean = false,
    val userMessage: UiText? = null,
    val stories: List<Story>? = null,
    val selectedStory: Story? = null

)

@HiltViewModel
class MapViewViewModel @Inject constructor(
    private val storiesRepository: GetAllStoriesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapViewUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllStoriesWithLocation()
    }

    private fun getAllStoriesWithLocation() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            storiesRepository.getAllStories(location = 1).collect { storiesResponseAsync ->
                _uiState.update { mapViewUiState ->
                    produceMapViewUiState(
                        storiesResponseAsync = storiesResponseAsync,
                        mapViewUiState = mapViewUiState
                    )
                }
            }
        }
    }

    private fun produceMapViewUiState(
        storiesResponseAsync: Async<GetAllStoriesResponse>,
        mapViewUiState: MapViewUiState,
    ) =
        when (storiesResponseAsync) {
            Async.Loading -> MapViewUiState(isLoading = true, isEmpty = true)

            is Async.Error -> mapViewUiState.copy(
                isEmpty = true,
                isLoading = false,
                userMessage = UiText.DynamicString(storiesResponseAsync.errorMessage)
            )

            is Async.Success -> {
                val stories = storiesResponseAsync.data.listStory ?: listOf()
                mapViewUiState.copy(
                    isEmpty = stories.isEmpty(), isLoading = false, stories = stories
                )
            }
        }

    fun setSelectedMapStory(story: Story?) {
        _uiState.update {
            it.copy(selectedStory = story)
        }
    }

    fun clearSelectedMapStory() {
        _uiState.update {
            it.copy(selectedStory = null)
        }
    }

    fun toastMessageShown() {
        _uiState.update {
            it.copy(
                userMessage = null
            )
        }
    }

    fun hasLocationPermission(context: Context) = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}