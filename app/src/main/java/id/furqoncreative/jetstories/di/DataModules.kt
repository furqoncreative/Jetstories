package id.furqoncreative.jetstories.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig.BASE_URL
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.data.source.network.JestoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
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
