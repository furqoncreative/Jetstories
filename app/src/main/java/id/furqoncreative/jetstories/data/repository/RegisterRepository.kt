package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.util.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

interface RegisterRepository {
    suspend fun registerUser(
        email: String,
        name: String,
        password: String
    ): Flow<Async<RegisterResponse>>
}

class NetworkRegisterRepository(
    private val networkDataSource: NetworkDataSource
) : RegisterRepository {
    override suspend fun registerUser(
        email: String, name: String, password: String
    ): Flow<Async<RegisterResponse>> {
        return networkDataSource.registerUser(email, name, password).map {
            if (it.error) {
                Async.Error(R.string.universal_error_message)
            } else {
                Async.Success(it)
            }
        }.catch {
            Async.Error(R.string.universal_error_message)
        }
    }

}