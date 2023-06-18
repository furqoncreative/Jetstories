package id.furqoncreative.jetstories.fake

import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.model.login.LoginResponse

class FakeNetworkLoginRepository: LoginRepository {
    override suspend fun loginUser(email: String, password: String): LoginResponse {
        return FakeDataSource.successLoginResponse
    }
}