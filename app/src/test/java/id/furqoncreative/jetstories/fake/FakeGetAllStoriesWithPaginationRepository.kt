package id.furqoncreative.jetstories.fake

import androidx.paging.PagingData
import id.furqoncreative.jetstories.data.repository.GetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeGetAllStoriesWithPaginationRepository : GetAllStoriesWithPaginationRepository {
    private val flow = MutableSharedFlow<PagingData<StoryItem>>()
    suspend fun emit(value: PagingData<StoryItem>) = flow.emit(value)
    override suspend fun getStoriesWithPagination(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<Async<PagingData<StoryItem>>> = flow.map { Async.Success(it) }



    override suspend fun searchStories(
        page: Int?,
        size: Int?,
        query: String?,
        location: Int?
    ): Flow<Async<PagingData<StoryItem>>> {
        TODO("Not yet implemented")
    }

}