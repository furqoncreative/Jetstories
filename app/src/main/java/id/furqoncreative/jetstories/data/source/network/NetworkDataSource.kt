package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun loginUser(
        email: String, password: String
    ): Flow<LoginResponse>

    suspend fun registerUser(
        email: String, name: String, password: String
    ): Flow<RegisterResponse>


    suspend fun getAllStories(
        token: String, page: Int?, size: Int?, location: Int?
    ): Flow<GetAllStoriesResponse>
}