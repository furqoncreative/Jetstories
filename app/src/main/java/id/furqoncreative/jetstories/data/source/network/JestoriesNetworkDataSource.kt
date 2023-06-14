package id.furqoncreative.jetstories.data.source.network

import javax.inject.Inject

class JestoriesNetworkDataSource @Inject constructor(
    private val appService: JetstoriesApiService
) : NetworkDataSource {
    override suspend fun loginUser(email: String, password: String) =
        appService.loginUser(email, password)
}