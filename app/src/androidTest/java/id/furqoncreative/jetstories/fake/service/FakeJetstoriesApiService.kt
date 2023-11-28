package id.furqoncreative.jetstories.fake.service

import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.login.LoginResult
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.AddStoryResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.model.stories.GetDetailStoryResponse
import id.furqoncreative.jetstories.model.stories.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

data class UserCredentials(
    val userId: String,
    val name: String,
    val email: String,
    val password: String,
    val token: String
)

class FakeJetstoriesApiService @Inject constructor() : JetstoriesApiService {
    val users = mutableListOf<UserCredentials>()
    val listStory = mutableListOf<Story>()

    init {
        users.add(
            UserCredentials(
                userId = "jetstories",
                name = "Jetstories",
                email = "jetstories@mail.com",
                password = "jetstories123",
                token = "jetstories123jetstories123"
            )
        )

        listStory.add(
            Story(
                createdAt = "2023",
                description = "I Love Jetpack Compose",
                id = "story-123",
                lat = null,
                lon = null,
                name = "Jetstories",
                photoUrl = "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/5bb2e531a22c7de0_856.png?hl=id"
            )
        )
    }

    override suspend fun loginUser(email: String, password: String): LoginResponse {
        return users.find { it.email == email && it.password == password }.let {
            if (it != null) {
                LoginResponse(
                    error = false,
                    loginResult = LoginResult(
                        userId = it.userId,
                        token = it.token,
                        name = it.name
                    ),
                    message = "Success"
                )
            } else {
                LoginResponse(
                    error = true,
                    loginResult = null,
                    message = "Login Failed"
                )
            }
        }
    }

    override suspend fun registerUser(
        email: String,
        name: String,
        password: String
    ): RegisterResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Double?,
        longitude: Double?
    ): AddStoryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int?
    ): GetAllStoriesResponse {
        return GetAllStoriesResponse(
            error = false,
            listStory = listOf(),
            message = "Success"
        )
    }

    override suspend fun getDetailStory(token: String, id: String): GetDetailStoryResponse {
        TODO("Not yet implemented")
    }

}