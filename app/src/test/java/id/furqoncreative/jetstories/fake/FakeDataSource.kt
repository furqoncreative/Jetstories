package id.furqoncreative.jetstories.fake

import id.furqoncreative.jetstories.model.login.LoginResponse

object FakeDataSource {
    val errorLoginResponse = LoginResponse(
        error = true,
        message = "\\\"email\\\" must be a valid email",
        loginResult = null
    )
}