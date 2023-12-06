package id.furqoncreative.jetstories.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.RegisterRepository
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.ui.components.states.ConfirmPasswordState
import id.furqoncreative.jetstories.ui.components.states.EmailState
import id.furqoncreative.jetstories.ui.components.states.NameState
import id.furqoncreative.jetstories.ui.components.states.PasswordState
import id.furqoncreative.jetstories.utils.Async
import id.furqoncreative.jetstories.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RegisterUiState(
    val emailState: EmailState = EmailState(),
    val nameState: NameState = NameState(),
    val passwordState: PasswordState = PasswordState(),
    val confirmPasswordState: ConfirmPasswordState = ConfirmPasswordState(passwordState),
    val isLoading: Boolean = false,
    val userMessage: UiText? = null,
    val isSuccessRegister: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            registerRepository.registerUser(
                uiState.value.emailState.text,
                uiState.value.nameState.text,
                uiState.value.passwordState.text
            ).collect { registerResponseAsync ->
                _uiState.update { registerUiState ->
                    produceRegisterUiState(
                        registerResponseAsync = registerResponseAsync,
                        registerUiState = registerUiState
                    )
                }
            }
        }
    }

    private fun produceRegisterUiState(
        registerResponseAsync: Async<RegisterResponse>,
        registerUiState: RegisterUiState
    ) = when (registerResponseAsync) {
        Async.Loading -> registerUiState.copy(isLoading = true)

        is Async.Error -> registerUiState.copy(
            userMessage = UiText.DynamicString(registerResponseAsync.errorMessage),
            isLoading = false,
            isSuccessRegister = false
        )

        is Async.Success -> {
            if (!registerResponseAsync.data.error) {
                registerUiState.copy(
                    isLoading = false,
                    isSuccessRegister = true,
                    userMessage = UiText.StringResource(R.string.signed_up)
                )
            } else {
                registerUiState.copy(
                    emailState = uiState.value.emailState,
                    nameState = uiState.value.nameState,
                    passwordState = uiState.value.passwordState,
                    confirmPasswordState = uiState.value.confirmPasswordState,
                    isLoading = false,
                    isSuccessRegister = false,
                    userMessage = UiText.DynamicString(registerResponseAsync.data.message)
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