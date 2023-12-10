package id.furqoncreative.jetstories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig
import id.furqoncreative.jetstories.data.source.network.JetstoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.interceptor.AuthInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {
    @Provides
    @Singleton
    fun okHttpCallFactory(authInterceptor: AuthInterceptor): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            },
        )
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Provides
    fun provideAppService(okhttpCallFactory: Call.Factory): JetstoriesApiService =
        Retrofit.Builder()
            .callFactory(okhttpCallFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JetstoriesApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        appService: JetstoriesApiService,
    ) = JetstoriesNetworkDataSource(appService)
}