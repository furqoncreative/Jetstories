package id.furqoncreative.jetstories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.fake.service.FakeJetstoriesApiService

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkDataModule::class]
)
interface FakeNetworkModule {
    @Binds
    fun provideAppService(repository: FakeJetstoriesApiService): JetstoriesApiService
}