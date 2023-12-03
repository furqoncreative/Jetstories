package id.furqoncreative.jetstories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import javax.inject.Singleton

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