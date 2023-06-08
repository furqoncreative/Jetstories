package id.furqoncreative.jetstories.data

import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.fake.FakeApiService
import id.furqoncreative.jetstories.fake.FakeDataSource
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkLoginRepositoryTest {

    @Test
    fun givenInvalidEmailAndInvalidPasswordThenShouldFail() =
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
    fun givenInvalidEmailAndValidPasswordThenShouldFail() =
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
    fun givenValidEmailAndInvalidPasswordThenShouldFail() =
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

    @Test
    fun givenValidEmailAndPasswordLessThanEightThenShouldFail() =
        runTest {
            val actualResult = LoginResponse(
                error = true,
                message = "make sure your password is at least 8 characters",
                loginResult = null
            )

            val expectedResult = FakeDataSource.passwordLessThanEightLoginResponse

            val repository = NetworkLoginRepository(
                apiService = FakeApiService(actualResult)
            )

            assertEquals(
                expectedResult,
                repository.loginUser(email = "test@email.com", password = "123456")
            )
        }

    @Test
    fun givenValidEmailAndValidPasswordThenShouldSuccess() =
        runTest {
            val actualResult = LoginResponse(
                error = false,
                message = "success",
                loginResult = LoginResult(
                    userId = "user-GbQe_jea6jrj1lHE",
                    name = "test",
                    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUdiUWVfamVhNmpyajFsSEUiLCJpYXQiOjE2ODYyMTUyMTB9.TKj1c6v0Jgcf3Gwcr6ZBGaGpoEw24QiidbLmvzNU7j8"
                )
            )

            val expectedResult = FakeDataSource.successLoginResponse

            val repository = NetworkLoginRepository(
                apiService = FakeApiService(actualResult)
            )

            assertEquals(
                expectedResult,
                repository.loginUser(email = "test@email.com", password = "12345678")
            )
        }
}

