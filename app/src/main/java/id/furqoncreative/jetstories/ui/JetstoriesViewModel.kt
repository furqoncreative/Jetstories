package id.furqoncreative.jetstories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JetstoriesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            checkUserLoginStatus()
        }
    }

    private suspend fun checkUserLoginStatus() = preferencesManager.getUserToken.collect { token ->
        if (token.isNotEmpty()) {
            _uiState.update { true }
        }
    }
}