package id.furqoncreative.jetstories.ui.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult
import id.furqoncreative.jetstories.ui.components.EmailState
import id.furqoncreative.jetstories.ui.components.PasswordState
import id.furqoncreative.jetstories.util.Async
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
    val userMessage: Int? = null,
    val loginResult: LoginResult? = null,
    val isSuccessLogin: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepository,
    val preferencesManager: PreferencesManager
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
            userMessage = loginAsync.errorMessage, isLoading = false, isSuccessLogin = false
        )

        is Async.Success -> {
            val loginResult = loginAsync.data.loginResult
            if (loginResult != null) {
                preferencesManager.setUserToken(loginResult.token)
                LoginUiState(
                    loginResult = loginResult, isLoading = false, isSuccessLogin = true
                )
            } else {
                LoginUiState(
                    emailState = uiState.value.emailState,
                    passwordState = uiState.value.passwordState,
                    isLoading = false,
                    isSuccessLogin = false,
                    userMessage = R.string.user_not_found
                )
            }
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(
                userMessage = null
            )
        }
    }
}