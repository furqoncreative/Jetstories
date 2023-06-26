package id.furqoncreative.jetstories.ui.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.EmailState
import id.furqoncreative.jetstories.utils.PasswordState
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val emailState: EmailState = EmailState(),
    val passwordState: PasswordState = PasswordState(),
    val isLoading: Boolean = false,
    val userMessage: UiText? = null,
    val loginResult: LoginResult? = null,
    val isSuccessLogin: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository, private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginUser() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            loginRepository.loginUser(
                uiState.value.emailState.text, uiState.value.passwordState.text
            ).collect { loginAsync ->
                _uiState.update {
                    produceLoginUiState(loginAsync)
                }
            }
        }
    }

    private suspend fun produceLoginUiState(loginAsync: Async<LoginResponse>) = when (loginAsync) {
        Async.Loading -> LoginUiState(isLoading = true)

        is Async.Error -> LoginUiState(
            userMessage = UiText.DynamicString(loginAsync.errorMessage),
            isLoading = false,
            isSuccessLogin = false
        )

        is Async.Success -> {
            val loginResult = loginAsync.data.loginResult
            if (loginResult != null) {
                preferencesManager.setUserToken(loginResult.token)
                LoginUiState(
                    loginResult = loginResult,
                    isLoading = false,
                    isSuccessLogin = true,
                    userMessage = UiText.StringResource(R.string.logged_in)
                )
            } else {
                LoginUiState(
                    emailState = uiState.value.emailState,
                    passwordState = uiState.value.passwordState,
                    isLoading = false,
                    isSuccessLogin = false,
                    userMessage = UiText.DynamicString(loginAsync.data.message)
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
}