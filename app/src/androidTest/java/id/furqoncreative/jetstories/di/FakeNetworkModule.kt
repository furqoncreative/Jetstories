package id.furqoncreative.jetstories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import id.furqoncreative.jetstories.BuildConfig
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.fake.service.FakeJetstoriesApiService
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkDataModules::class]
)
interface FakeNetworkModule {
    @Binds
    fun provideAppService(repository: FakeJetstoriesApiService): JetstoriesApiService
}