package id.furqoncreative.jetstories.data

import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.fake.FakeApiService
import id.furqoncreative.jetstories.fake.FakeDataSource
import id.furqoncreative.jetstories.model.login.LoginResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkLoginRepositoryTest {

    @Test
    fun givenInvalidUsernameAndInvalidPasswordThenShouldFail() =
        runTest {
            val actualResult = LoginResponse(
                error = true,
                message = "\\\"email\\\" must be a valid email",
                loginResult = null
            )

            val expectedResult = FakeDataSource.emailErrorLoginResponse

            val repository = NetworkLoginRepository(
                apiService = FakeApiService(actualResult)
            )

            assertEquals(
                expectedResult,
                repository.loginUser(email = "email.com", password = "1234")
            )
        }

    @Test
    fun givenInvalidUsernameAndValidPasswordThenShouldFail() =
        runTest {
            val actualResult = LoginResponse(
                error = true,
                message = "\\\"email\\\" must be a valid email",
                loginResult = null
            )

            val expectedResult = FakeDataSource.emailErrorLoginResponse

            val repository = NetworkLoginRepository(
                apiService = FakeApiService(actualResult)
            )

            assertEquals(
                expectedResult,
                repository.loginUser(email = "email.com", password = "12345678")
            )
        }

    @Test
    fun givenValidUsernameAndInvalidPasswordThenShouldFail() =
        runTest {
            val actualResult = LoginResponse(
                error = true,
                message = "User not found",
                loginResult = null
            )

            val expectedResult = FakeDataSource.userNotFoundLoginResponse

            val repository = NetworkLoginRepository(
                apiService = FakeApiService(actualResult)
            )

            assertEquals(
                expectedResult,
                repository.loginUser(email = "test@email.com", password = "123456789")
            )
        }
}

