package id.furqoncreative.jetstories.data.source.network

import com.google.gson.Gson
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject


class JestoriesNetworkDataSource @Inject constructor(
    private val appService: JetstoriesApiService
) : NetworkDataSource {
    override suspend fun loginUser(email: String, password: String): Flow<LoginResponse> = flow {
        try {
            val response = appService.loginUser(email, password)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                LoginResponse(
                    error = true, message = e.message, loginResult = null
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun registerUser(
        email: String, name: String, password: String
    ): Flow<RegisterResponse> = flow {
        try {
            val response = appService.registerUser(email, name, password)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                RegisterResponse(
                    error = true, message = e.message
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllStories(
        token: String, page: Int?, size: Int?, location: Int?
    ): Flow<GetAllStoriesResponse> = flow {
        try {
            val response = appService.getAllStories(token, page, size, location)
            emit(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, GetAllStoriesResponse::class.java)
            emit(errorResponse)
        } catch (e: Exception) {
            emit(
                GetAllStoriesResponse(
                    error = true, message = e.message, listStory = null
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}