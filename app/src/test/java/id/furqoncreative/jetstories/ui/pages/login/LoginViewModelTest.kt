package id.furqoncreative.jetstories.ui.pages.login

import id.furqoncreative.jetstories.fake.FakeDataSource
import id.furqoncreative.jetstories.fake.FakeNetworkLoginRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginViewModelTest {

    @Test
    fun whenUserSuccessLoginThenVerifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginRepository = FakeNetworkLoginRepository()
        )

        assertEquals(
            LoginUiState.Success(FakeDataSource.successLoginResponse),
            loginViewModel.loginUiState
        )
    }
}