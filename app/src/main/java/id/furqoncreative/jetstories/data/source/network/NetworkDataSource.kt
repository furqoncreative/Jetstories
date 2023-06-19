package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun loginUser(
        email: String, password: String
    ): Flow<LoginResponse>

    suspend fun getAllStories(
        page: Int?, size: Int?, location: Int?
    ): Flow<GetAllStoriesResponse>
}