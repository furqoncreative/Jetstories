package id.furqoncreative.jetstories.fake

import androidx.paging.PagingData
import id.furqoncreative.jetstories.data.repository.GetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeGetAllStoriesWithPaginationRepository : GetAllStoriesWithPaginationRepository {
    private val flow = MutableSharedFlow<PagingData<Story>>()
    suspend fun emit(value: PagingData<Story>) = flow.emit(value)
    override suspend fun getStoriesWithPagination(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<Async<PagingData<Story>>> = flow.map {
        Async.Success(it)
    }


    override suspend fun searchStories(
        page: Int?,
        size: Int?,
        query: String?,
        location: Int?
    ): Flow<Async<PagingData<Story>>> {
        TODO("Not yet implemented")
    }

}