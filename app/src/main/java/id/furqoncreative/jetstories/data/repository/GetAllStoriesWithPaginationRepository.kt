package id.furqoncreative.jetstories.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
import id.furqoncreative.jetstories.data.source.mapper.toStory
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.data.source.remotemediator.StoryRemoteMediator
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface GetAllStoriesWithPaginationRepository {
    suspend fun getStoriesWithPagination(
        page: Int? = null,
        size: Int? = null,
        location: Int? = 0
    ): Flow<Async<PagingData<Story>>>

    suspend fun searchStories(
        page: Int? = null,
        size: Int? = null,
        query: String? = null,
        location: Int? = 0
    ): Flow<Async<PagingData<Story>>>
}

@OptIn(ExperimentalPagingApi::class)
@Singleton
class NetworkGetAllStoriesWithPaginationRepository @Inject constructor(
    private val storyDatabase: StoryDatabase,
    private val apiService: JetstoriesApiService
) : GetAllStoriesWithPaginationRepository {

    override suspend fun getStoriesWithPagination(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<Async<PagingData<Story>>> = Pager(
        config = PagingConfig(
            pageSize = size ?: 10,
            enablePlaceholders = false,
            initialLoadSize = 1
        ), remoteMediator = StoryRemoteMediator(
            database = storyDatabase,
            apiService = apiService
        ),
        pagingSourceFactory = {
            storyDatabase.storyDao().getAllStories()
        }).flow.map { pagingData ->
        Async.Success(pagingData.map { it.toStory() })
    }.catch {
        Async.Error(it.message.toString())
    }

    override suspend fun searchStories(
        page: Int?,
        size: Int?,
        query: String?,
        location: Int?
    ): Flow<Async<PagingData<Story>>> = Pager(
        config = PagingConfig(
            pageSize = size ?: 10,
            enablePlaceholders = false,
            initialLoadSize = 1
        ),
        pagingSourceFactory = {
            storyDatabase.storyDao().getStories(query)
        }).flow.map { pagingData ->
        Async.Success(pagingData.map { it.toStory() })
    }.catch {
        Async.Error(it.message.toString())
    }

}