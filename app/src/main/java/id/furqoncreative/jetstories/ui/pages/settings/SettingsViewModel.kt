package id.furqoncreative.jetstories.ui.pages.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.utils.updateResources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LanguageEnum(val title: String, val code: String, val icon: String) {
    ENGLISH(title = "English", code = "en", icon = "🇬🇧"), INDONESIA(
        title = "Bahasa Indonesia", code = "id", icon = "🇮🇩"
    )
}

data class SettingsUiState(
    val selectedLanguageEnum: LanguageEnum,
    val userMessage: String? = null,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState(LanguageEnum.ENGLISH))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            setSelectedLanguageFromAppLanguage()
        }
    }

    fun setAppLanguage(context: Context) {
        val selectedLanguage = uiState.value.selectedLanguageEnum
        viewModelScope.launch {
            try {
                updateResources(context, selectedLanguage.code)
                preferencesManager.setAppLanguage(selectedLanguage.name)
            } finally {
                setSelectedLanguageFromAppLanguage()
            }
        }
    }

    private suspend fun setSelectedLanguageFromAppLanguage() {
        val appLanguage = LanguageEnum.valueOf(preferencesManager.getAppLanguage.first())
        setSelectedLanguage(appLanguage)
    }

    fun setSelectedLanguage(languageEnum: LanguageEnum) {
        _uiState.update {
            it.copy(
                selectedLanguageEnum = languageEnum
            )
        }
    }

}