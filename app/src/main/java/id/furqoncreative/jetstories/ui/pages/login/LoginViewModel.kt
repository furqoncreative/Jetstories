package id.furqoncreative.jetstories.ui.pages.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import id.furqoncreative.jetstories.JetstoriesApplication
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.model.login.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface LoginUiState {
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    object Error : LoginUiState
    object Loading : LoginUiState
    object Idle : LoginUiState
}

class LoginViewModel(
    val loginRepository: LoginRepository
) : ViewModel() {

    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                LoginUiState.Success(loginRepository.loginUser(email, password))
            } catch (e: Exception) {
                Log.d("TAG", "loginUser: $e")
                LoginUiState.Error
            } catch (e: HttpException) {
                Log.d("TAG", "loginUser: $e")
                LoginUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as JetstoriesApplication)
                val loginRepository = application.container.loginRepository
                LoginViewModel(loginRepository = loginRepository)
            }
        }
    }
}