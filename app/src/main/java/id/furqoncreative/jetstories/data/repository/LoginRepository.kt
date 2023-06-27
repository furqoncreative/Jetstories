package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.utils.Async
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
            if (it.error) {
                Async.Error(it.message)
            } else {
                Async.Success(it)
            }
        }.catch { throwable ->
            throwable.message?.let { Async.Error(it) }
        }
    }
}