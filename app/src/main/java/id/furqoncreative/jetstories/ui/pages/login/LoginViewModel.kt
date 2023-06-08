package id.furqoncreative.jetstories.ui.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.model.login.LoginResponse

sealed interface LoginUiState {
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    object Error : LoginUiState
    object Loading : LoginUiState
}

class LoginViewModel(
    loginRepository: LoginRepository
) {
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set
}