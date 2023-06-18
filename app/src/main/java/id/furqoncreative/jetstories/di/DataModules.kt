package id.furqoncreative.jetstories.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig
import id.furqoncreative.jetstories.BuildConfig.BASE_URL
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModules {

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
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
        .create(JetstoriesApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        appService: JetstoriesApiService,
    ) = JestoriesNetworkDataSource(appService)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(repository: NetworkLoginRepository): LoginRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: JestoriesNetworkDataSource): NetworkDataSource
}
