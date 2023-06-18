package id.furqoncreative.jetstories.ui.pages.login

import id.furqoncreative.jetstories.rules.TestDispatcherRule
import id.furqoncreative.jetstories.fake.FakeDataSource
import id.furqoncreative.jetstories.fake.FakeNetworkLoginRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun whenUserSuccessLoginThenVerifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginRepository = FakeNetworkLoginRepository()
        )

        loginViewModel.loginUser(
            email = "test@email.com",
            password = "12345678",
        )

        assertEquals(
            LoginUiState.Success(FakeDataSource.successLoginResponse), loginViewModel.loginUiState
        )
    }
}