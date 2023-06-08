package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.response.JetstoriesApiService
import id.furqoncreative.jetstories.model.login.LoginResponse

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): LoginResponse
}

class NetworkLoginRepository(
    private val apiService: JetstoriesApiService
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String) =
        apiService.loginUser(email, password)
}