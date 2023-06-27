package id.furqoncreative.jetstories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.ui.pages.settings.LanguageEnum
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JetstoriesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _appLanguage = MutableStateFlow(LanguageEnum.ENGLISH.code)

    val isLoggedIn = preferencesManager.getUserToken.map {
        it.isNotEmpty()
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = false
    )

    init {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = false
        }
    }

    fun getUpdateAppLanguage(): String {
        viewModelScope.launch {
            val language = preferencesManager.getAppLanguage.first()
            _appLanguage.update {
                LanguageEnum.valueOf(language).code
            }
        }
        return _appLanguage.asStateFlow().value
    }
}