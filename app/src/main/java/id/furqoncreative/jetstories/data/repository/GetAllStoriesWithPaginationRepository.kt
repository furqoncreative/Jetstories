package id.furqoncreative.jetstories.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.data.source.local.StoryRemoteMediator
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface GetAllStoriesWithPaginationRepository {
    suspend fun getAllStoriesWithPagination(
        page: Int? = null, size: Int? = null, location: Int? = 0
    ): Flow<Async<PagingData<StoryItem>>>
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
    ): Flow<Async<PagingData<StoryItem>>> {

        return Pager(config = PagingConfig(
            pageSize = size ?: 10
        ), remoteMediator = StoryRemoteMediator(
            preferencesManager, storyDatabase, apiService
        ), pagingSourceFactory = {
            storyDatabase.storyDao().getAllStories()
        }).flow.map {
            Async.Success(it)
        }.catch {
            Async.Error(it.message.toString())
        }

    }
}