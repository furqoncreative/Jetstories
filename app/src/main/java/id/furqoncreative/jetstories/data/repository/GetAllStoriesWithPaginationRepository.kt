package id.furqoncreative.jetstories.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Provides
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.data.source.local.StoryRemoteMediator
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface GetAllStoriesWithPaginationRepository {
    suspend fun getAllStoriesWithPagination(
        page: Int? = null, size: Int? = null, location: Int? = 0
    ): Flow<Async<Pager<Int, StoryItem>>>
}

@Singleton
class NetworkGetAllStoriesWithPaginationRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val storyDatabase: StoryDatabase,
    private val apiService: JetstoriesApiService
) : GetAllStoriesWithPaginationRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getAllStoriesWithPagination(
        page: Int?, size: Int?, location: Int?
    ): Flow<Async<Pager<Int, StoryItem>>> {

        val storyPager = Pager(config = PagingConfig(
            pageSize = 10
        ), remoteMediator = StoryRemoteMediator(
            preferencesManager, storyDatabase, apiService
        ), pagingSourceFactory = {
            storyDatabase.storyDao().getAllStories()
        })

        return flow {
            try {
                emit(Async.Success(storyPager))
            } catch (e: Exception) {
                emit(Async.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }
}