package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse

interface NetworkDataSource {
    suspend fun loginUser(
        email: String, password: String
    ): LoginResponse
}