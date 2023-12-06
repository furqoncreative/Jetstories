package id.furqoncreative.jetstories.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult
import id.furqoncreative.jetstories.ui.components.states.EmailState
import id.furqoncreative.jetstories.ui.components.states.PasswordState
import id.furqoncreative.jetstories.utils.Async
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
    val isLoginSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferencesManager: PreferencesManager
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
            ).collect { loginResponseAsync ->
                _uiState.update { loginUiState ->
                    produceLoginUiState(
                        loginResponseAsync = loginResponseAsync,
                        loginUiState = loginUiState
                    )
                }
            }
        }
    }

    private suspend fun produceLoginUiState(
        loginResponseAsync: Async<LoginResponse>,
        loginUiState: LoginUiState
    ) = when (loginResponseAsync) {
        Async.Loading -> loginUiState.copy(isLoading = true)

        is Async.Error -> loginUiState.copy(
            userMessage = UiText.DynamicString(loginResponseAsync.errorMessage),
            isLoading = false,
            isLoginSuccess = false
        )

        is Async.Success -> {
            val loginResult = loginResponseAsync.data.loginResult
            if (loginResult != null) {
                preferencesManager.setUserToken(loginResult.token)
                loginUiState.copy(
                    loginResult = loginResult,
                    isLoading = false,
                    isLoginSuccess = true,
                    userMessage = UiText.StringResource(R.string.logged_in)
                )
            } else {
                loginUiState.copy(
                    emailState = uiState.value.emailState,
                    passwordState = uiState.value.passwordState,
                    isLoading = false,
                    isLoginSuccess = false,
                    userMessage = UiText.StringResource(R.string.user_not_found)
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