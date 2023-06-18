package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JestoriesNetworkDataSource @Inject constructor(
    private val appService: JetstoriesApiService
) : NetworkDataSource {
    override suspend fun loginUser(email: String, password: String): Flow<LoginResponse> = flow {
        try {
            val response = appService.loginUser(email, password)
            emit(response)
        } catch (e: Exception) {
            emit(
                LoginResponse(
                    error = true, message = e.message, loginResult = null
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}