package id.furqoncreative.jetstories.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.data.source.network.response.JetstoriesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val loginRepository: LoginRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://story-api.dicoding.dev/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val retrofitService: JetstoriesApiService by lazy {
        retrofit.create(JetstoriesApiService::class.java)
    }

    override val loginRepository: LoginRepository by lazy {
        NetworkLoginRepository(retrofitService)
    }
}
