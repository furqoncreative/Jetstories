package id.furqoncreative.jetstories.ui.screens.addstory

import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.AddStoryRepository
import id.furqoncreative.jetstories.model.stories.AddStoryResponse
import id.furqoncreative.jetstories.ui.components.states.DescriptionState
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import id.furqoncreative.jetstories.utils.reduceFileImage
import id.furqoncreative.jetstories.utils.toFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


data class AddStoryUiState(
    val descriptionState: DescriptionState = DescriptionState(),
    val isLoading: Boolean = false,
    val imageUri: Uri? = null,
    val location: Location? = null,
    val userMessage: UiText? = null,
    val isSuccessAddStory: Boolean = false
)

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val addStoryRepository: AddStoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddStoryUiState())
    val uiState = _uiState.asStateFlow()

    fun addStory(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val imageUri = uiState.value.imageUri
            val location = uiState.value.location
            if (imageUri != null) {
                val file: File = imageUri.toFile(context).reduceFileImage()
                val description =
                    uiState.value.descriptionState.text.toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo", file.name, requestImageFile
                )

                addStoryRepository.addStory(
                    file = imageMultipart,
                    description = description,
                    latitude = location?.latitude,
                    longitude = location?.longitude
                ).collect { addStoryResponseAsync ->
                    _uiState.update { addStoryUiState ->
                        produceAddStoryUiState(
                            addStoryResponseAsync = addStoryResponseAsync,
                            addStoryUiState = addStoryUiState
                        )
                    }
                }
            } else {
                return@launch
            }
        }
    }

    private fun produceAddStoryUiState(
        addStoryResponseAsync: Async<AddStoryResponse>,
        addStoryUiState: AddStoryUiState
    ) = when (addStoryResponseAsync) {
        Async.Loading -> addStoryUiState.copy(isLoading = true)

        is Async.Error -> addStoryUiState.copy(
            userMessage = UiText.DynamicString(addStoryResponseAsync.errorMessage),
            isLoading = false,
            isSuccessAddStory = false
        )

        is Async.Success -> {
            if (!addStoryResponseAsync.data.error) {
                addStoryUiState.copy(
                    isLoading = false,
                    isSuccessAddStory = true,
                    userMessage = UiText.StringResource(
                        resId = R.string.success_added_story
                    )
                )
            } else {
                addStoryUiState.copy(
                    descriptionState = uiState.value.descriptionState,
                    isLoading = false,
                    isSuccessAddStory = false,
                    userMessage = UiText.DynamicString(addStoryResponseAsync.data.message)
                )
            }
        }
    }

    fun toastMessageShown() {
        _uiState.update {
            it.copy(
                userMessage = null
            )
        }
    }

    fun setImageUri(imageUri: Uri?) {
        _uiState.update {
            it.copy(
                imageUri = imageUri
            )
        }
    }

    fun setLocation(location: Location?) {
        _uiState.update {
            it.copy(
                location = location
            )
        }
    }

    fun setUserMessage(message: String) {
        _uiState.update {
            it.copy(
                userMessage = UiText.DynamicString(message)
            )
        }
    }
}