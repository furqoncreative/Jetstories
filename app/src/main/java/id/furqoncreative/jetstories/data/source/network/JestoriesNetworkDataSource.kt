package id.furqoncreative.jetstories.data.source.network

import com.google.gson.Gson
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.AddStoryResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.model.stories.GetDetailStoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject


class JestoriesNetworkDataSource @Inject constructor(
    private val apiService: JetstoriesApiService
) : NetworkDataSource {
    override suspend fun loginUser(email: String, password: String): Flow<LoginResponse> = flow {
        try {
            val response = apiService.loginUser(email, password)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                LoginResponse(
                    error = true, message = e.message.toString(), loginResult = null
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun registerUser(
        email: String, name: String, password: String
    ): Flow<RegisterResponse> = flow {
        try {
            val response = apiService.registerUser(email, name, password)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                RegisterResponse(
                    error = true, message = e.message.toString()
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllStories(
        token: String, page: Int?, size: Int?, location: Int?
    ): Flow<GetAllStoriesResponse> = flow {
        try {
            val response = apiService.getAllStories(token, page, size, location)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetAllStoriesResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                GetAllStoriesResponse(
                    error = true, message = e.message, listStory = listOf()
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Float?,
        longitude: Float?
    ): Flow<AddStoryResponse> = flow {
        try {
            val response = apiService.addStory(token, file, description, latitude, longitude)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddStoryResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                AddStoryResponse(
                    error = true, message = e.message.toString()
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDetailStory(token: String, id: String): Flow<GetDetailStoryResponse> =
        flow {
            try {
                val response = apiService.getDetailStory(token, id)
                emit(response)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, GetDetailStoryResponse::class.java)
                emit(errorResponse)
            } catch (e: Exception) {
                emit(
                    GetDetailStoryResponse(
                        error = true, message = e.message, story = null
                    )
                )
            }
        }
}