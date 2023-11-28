package id.furqoncreative.jetstories.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.BuildConfig
import id.furqoncreative.jetstories.BuildConfig.BASE_URL
import id.furqoncreative.jetstories.data.repository.AddStoryRepository
import id.furqoncreative.jetstories.data.repository.GetAllStoriesRepository
import id.furqoncreative.jetstories.data.repository.GetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.data.repository.GetDetailStoryRepository
import id.furqoncreative.jetstories.data.repository.LoginRepository
import id.furqoncreative.jetstories.data.repository.NetworkAddStoryRepository
import id.furqoncreative.jetstories.data.repository.NetworkGetAllStoriesRepository
import id.furqoncreative.jetstories.data.repository.NetworkGetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.data.repository.NetworkGetDetailStoryRepository
import id.furqoncreative.jetstories.data.repository.NetworkLoginRepository
import id.furqoncreative.jetstories.data.repository.NetworkRegisterRepository
import id.furqoncreative.jetstories.data.repository.RegisterRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
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
object LocalDataModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jetstories-preferences")

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext app: Context): DataStore<Preferences> = app.dataStore

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): StoryDatabase = StoryDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providePreferencesManager(dataStore: DataStore<Preferences>): PreferencesManager =
        PreferencesManager(dataStore)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(repository: NetworkLoginRepository): LoginRepository

    @Singleton
    @Binds
    abstract fun bindRegisterRepository(repository: NetworkRegisterRepository): RegisterRepository

    @Singleton
    @Binds
    abstract fun bindGetAllStoryRepository(repository: NetworkGetAllStoriesRepository): GetAllStoriesRepository

    @Singleton
    @Binds
    abstract fun bindGetAllStoryWithPaginationRepository(repository: NetworkGetAllStoriesWithPaginationRepository): GetAllStoriesWithPaginationRepository

    @Singleton
    @Binds
    abstract fun bindGetDetailStoryRepository(repository: NetworkGetDetailStoryRepository): GetDetailStoryRepository

    @Singleton
    @Binds
    abstract fun bindAddStoryRepository(repository: NetworkAddStoryRepository): AddStoryRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: JestoriesNetworkDataSource): NetworkDataSource
}
