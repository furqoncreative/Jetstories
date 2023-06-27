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
            setSelectedLanguage(getAppLanguage())
        }
    }

    fun setAppLanguage() {
        viewModelScope.launch {
            try {
                val appLanguage = getAppLanguage()
                val selectedLanguage = uiState.value.selectedLanguageEnum
                if (appLanguage != selectedLanguage) {
                    preferencesManager.setAppLanguage(
                        selectedLanguage.name
                    )
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(selectedLanguage.code)
                    )
                    _uiState.update {
                        it.copy(
                            userMessage = UiText.StringResourceWithArgs(
                                R.string.language_changed_to,
                                uiState.value.selectedLanguageEnum.title
                            )
                        )
                    }
                }
                setSelectedLanguage(selectedLanguage)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        userMessage = UiText.StringResource(
                            R.string.universal_error_message
                        )
                    )
                }
            }
        }
    }

    fun setSelectedLanguage(languageEnum: LanguageEnum) {
        _uiState.update {
            it.copy(
                selectedLanguageEnum = languageEnum
            )
        }
    }

    suspend fun getAppLanguage() = LanguageEnum.valueOf(preferencesManager.getAppLanguage.first())

    fun toastMessageShown() {
        _uiState.update {
            it.copy(
                userMessage = null
            )
        }
    }
}