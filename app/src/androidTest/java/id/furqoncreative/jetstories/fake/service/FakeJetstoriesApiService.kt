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
    private val users = mutableListOf<UserCredentials>()
    private val listStory = mutableListOf<Story>()

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

    override suspend fun loginUser(
        email: String,
        password: String
    ): LoginResponse {
        val userCredential = users.firstOrNull { it.email == email }
            ?: return LoginResponse(
                error = true,
                loginResult = null,
                message = "User Not Found"
            )

        if (userCredential.password != password) {
            return LoginResponse(
                error = true,
                loginResult = null,
                message = "Invalid Password"
            )
        }

        return LoginResponse(
            error = false,
            loginResult = LoginResult(
                userId = userCredential.userId,
                token = userCredential.token,
                name = userCredential.name
            ),
            message = "Success"
        )
    }

    override suspend fun registerUser(
        email: String,
        name: String,
        password: String
    ): RegisterResponse {
        return if (users.find { it.email == email } == null) {
            RegisterResponse(
                error = false,
                message = "Register Success"
            )
        } else {
            RegisterResponse(
                error = true,
                message = "Email is already taken"
            )
        }
    }

    override suspend fun addStory(
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Double?,
        longitude: Double?
    ): AddStoryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): GetAllStoriesResponse {
        for (i in 0..100) {
            listStory.add(
                Story(
                    createdAt = "2023-$i",
                    description = "I Love Jetpack Compose $i",
                    id = "story-123-$i",
                    lat = null,
                    lon = null,
                    name = "Jetstories $i",
                    photoUrl = "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/5bb2e531a22c7de0_856.png?hl=id"
                )
            )
        }
        return GetAllStoriesResponse(
            error = false,
            listStory = listStory,
            message = "Success"
        )
    }

    override suspend fun getDetailStory(id: String): GetDetailStoryResponse {
        TODO("Not yet implemented")
    }

}