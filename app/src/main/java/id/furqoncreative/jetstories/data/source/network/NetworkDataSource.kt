package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.AddStoryResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.model.stories.GetDetailStoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface NetworkDataSource {
    suspend fun loginUser(
        email: String, password: String
    ): Flow<LoginResponse>

    suspend fun registerUser(
        email: String, name: String, password: String
    ): Flow<RegisterResponse>

    suspend fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Double?,
        longitude: Double?
    ): Flow<AddStoryResponse>

    suspend fun getAllStories(
        token: String, page: Int?, size: Int?, location: Int?
    ): Flow<GetAllStoriesResponse>

    suspend fun getDetailStory(
        token: String, id: String
    ): Flow<GetDetailStoryResponse>
}