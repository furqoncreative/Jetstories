package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import id.furqoncreative.jetstories.model.stories.GetDetailStoryResponse
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface GetDetailStoryRepository {
    suspend fun getDetailStory(id: String): Flow<Async<GetDetailStoryResponse>>
}

@Singleton
class NetworkGetDetailStoryRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : GetDetailStoryRepository {
    override suspend fun getDetailStory(id: String): Flow<Async<GetDetailStoryResponse>> =
        networkDataSource.getDetailStory(id = id).map {
            Async.Success(it)
        }.catch<Async<GetDetailStoryResponse>> { throwable ->
            throwable.message?.let { Async.Error(it) }
        }
}