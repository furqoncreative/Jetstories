package id.furqoncreative.jetstories.ui.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.model.login.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface LoginUiState {
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    object Error : LoginUiState
    object Loading : LoginUiState
}

class LoginViewModel(
    val loginRepository: LoginRepository
) : ViewModel() {

    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                LoginUiState.Success(loginRepository.loginUser(email, password))
            } catch (e: Exception) {
                LoginUiState.Error
            } catch (e: HttpException) {
                LoginUiState.Error
            }
        }
    }
}