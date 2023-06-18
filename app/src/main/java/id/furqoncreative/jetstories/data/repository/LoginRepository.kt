package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.util.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Flow<Async<LoginResponse>>
}

@Singleton
class NetworkLoginRepository @Inject constructor(
    private val networkDataSource: JestoriesNetworkDataSource
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String): Flow<Async<LoginResponse>> {
        return networkDataSource.loginUser(email, password).map {
            Async.Success(it)
        }.catch<Async<LoginResponse>> {
            Async.Error(R.string.universal_error_message)
        }
    }
}