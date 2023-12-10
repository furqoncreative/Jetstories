package id.furqoncreative.jetstories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.data.source.local.JetstoriesLocalDataSource
import id.furqoncreative.jetstories.data.source.local.LocalDataSource
import id.furqoncreative.jetstories.data.source.network.JetstoriesNetworkDataSource
import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: JetstoriesNetworkDataSource): NetworkDataSource


    @Singleton
    @Binds
    abstract fun bindLocalDataSource(dataSource: JetstoriesLocalDataSource): LocalDataSource
}

