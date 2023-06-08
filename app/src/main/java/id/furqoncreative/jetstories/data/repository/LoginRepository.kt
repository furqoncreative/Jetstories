package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.response.JetstoriesApiService

interface LoginRepository {

}

class NetworkLoginRepository(
    apiService: JetstoriesApiService
): LoginRepository {

}