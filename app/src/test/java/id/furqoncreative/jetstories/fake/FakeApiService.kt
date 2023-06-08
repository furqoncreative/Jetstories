package id.furqoncreative.jetstories.fake

import id.furqoncreative.jetstories.data.source.network.response.JetstoriesApiService
import id.furqoncreative.jetstories.model.login.LoginResponse

class FakeApiService(
    val response: Any
): JetstoriesApiService {
    override suspend fun loginUser(email: String, password: String): LoginResponse = response as LoginResponse
}