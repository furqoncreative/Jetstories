package id.furqoncreative.jetstories.fake

import id.furqoncreative.jetstories.model.login.LoginResponse

object FakeDataSource {
    val emailErrorLoginResponse = LoginResponse(
        error = true,
        message = "\\\"email\\\" must be a valid email",
        loginResult = null
    )

    val userNotFoundLoginResponse = LoginResponse(
        error = true,
        message = "User not found",
        loginResult = null
    )

    val passwordLessThanEightLoginResponse = LoginResponse(
        error = true,
        message = "make sure your password is at least 8 characters",
        loginResult = null
    )
}