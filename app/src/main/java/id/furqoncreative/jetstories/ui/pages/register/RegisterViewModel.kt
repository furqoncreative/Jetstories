package id.furqoncreative.jetstories.ui.pages.register

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.repository.RegisterRepository
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.ui.components.ConfirmPasswordState
import id.furqoncreative.jetstories.ui.components.EmailState
import id.furqoncreative.jetstories.ui.components.NameState
import id.furqoncreative.jetstories.ui.components.PasswordState
import id.furqoncreative.jetstories.util.Async
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
    val userMessage: String? = null,
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
            ).collect { registerAsync ->
                _uiState.update {
                    produceRegisterUiState(registerAsync)
                }
            }
        }
    }

    private fun produceRegisterUiState(register: Async<RegisterResponse>) = when (register) {
        Async.Loading -> RegisterUiState(isLoading = true)

        is Async.Error -> RegisterUiState(
            userMessage = register.errorMessage, isLoading = false, isSuccessRegister = false
        )

        is Async.Success -> {
            if (!register.data.error) {
                RegisterUiState(
                    isLoading = false,
                    isSuccessRegister = true,
                    userMessage = "Register Berhasil"
                )
            } else {
                RegisterUiState(
                    emailState = uiState.value.emailState,
                    nameState = uiState.value.nameState,
                    passwordState = uiState.value.passwordState,
                    confirmPasswordState = uiState.value.confirmPasswordState,
                    isLoading = false,
                    isSuccessRegister = false,
                    userMessage = Resources.getSystem().getString(R.string.universal_error_message)
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