package id.furqoncreative.jetstories.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig.BASE_URL
import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModules {

    @Provides
    fun provideAppService(): JetstoriesApiService = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build().create(JetstoriesApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        appService: JetstoriesApiService,
    ) = JestoriesNetworkDataSource(appService)
}