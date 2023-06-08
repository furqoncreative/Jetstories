package id.furqoncreative.jetstories.fake

import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult

object FakeDataSource {
    val emailErrorLoginResponse = LoginResponse(
        error = true, message = "\\\"email\\\" must be a valid email", loginResult = null
    )

    val userNotFoundLoginResponse = LoginResponse(
        error = true, message = "User not found", loginResult = null
    )

    val passwordLessThanEightLoginResponse = LoginResponse(
        error = true,
        message = "make sure your password is at least 8 characters",
        loginResult = null
    )

    val successLoginResponse = LoginResponse(
        error = false, message = "success", loginResult = LoginResult(
            userId = "user-GbQe_jea6jrj1lHE",
            name = "test",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUdiUWVfamVhNmpyajFsSEUiLCJpYXQiOjE2ODYyMTUyMTB9.TKj1c6v0Jgcf3Gwcr6ZBGaGpoEw24QiidbLmvzNU7j8"
        )
    )
}