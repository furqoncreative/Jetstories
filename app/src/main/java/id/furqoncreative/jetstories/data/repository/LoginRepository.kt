package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.model.login.LoginResponse
import javax.inject.Inject

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): LoginResponse
}

class NetworkLoginRepository @Inject constructor(
    private val networkDataSource: JestoriesNetworkDataSource
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String) =
        networkDataSource.loginUser(email, password)
}