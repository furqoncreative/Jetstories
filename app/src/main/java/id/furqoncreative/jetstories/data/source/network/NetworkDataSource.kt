package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun loginUser(
        email: String, password: String
    ): Flow<LoginResponse>
}