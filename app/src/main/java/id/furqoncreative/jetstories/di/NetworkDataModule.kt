package id.furqoncreative.jetstories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig
import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {
    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        },
    ).build()

    @Provides
    fun provideAppService(
        okhttpCallFactory: Call.Factory
    ): JetstoriesApiService = Retrofit.Builder().callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(BuildConfig.BASE_URL).build()
        .create(JetstoriesApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        appService: JetstoriesApiService,
    ) = JestoriesNetworkDataSource(appService)
}