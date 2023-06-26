package id.furqoncreative.jetstories.ui.pages.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.utils.UiText
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
    val userMessage: UiText? = null,
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

    fun setAppLanguage() {
        val selectedLanguage = uiState.value.selectedLanguageEnum
        viewModelScope.launch {
            try {
                preferencesManager.setAppLanguage(selectedLanguage.name)
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(selectedLanguage.code)
                )
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
            SettingsUiState(
                userMessage = UiText.StringResourceWithArgs(
                    R.string.language_changed_to,
                    languageEnum.title
                ), selectedLanguageEnum = languageEnum
            )
        }
    }
}