package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface RegisterRepository {
    suspend fun registerUser(
        email: String,
        name: String,
        password: String
    ): Flow<Async<RegisterResponse>>
}

@Singleton
class NetworkRegisterRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : RegisterRepository {
    override suspend fun registerUser(
        email: String,
        name: String,
        password: String
    ): Flow<Async<RegisterResponse>> = networkDataSource.registerUser(email, name, password).map {
        if (it.error) {
            Async.Error(it.message)
        } else {
            Async.Success(it)
        }
    }.catch { throwable ->
        throwable.message?.let { Async.Error(it) }
    }
}