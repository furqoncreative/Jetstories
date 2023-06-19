package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import id.furqoncreative.jetstories.util.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface GetAllStoriesRepository {
    suspend fun getAllStories(
        page: Int?, size: Int?, location: Int?
    ): Flow<Async<GetAllStoriesResponse>>
}

@Singleton
class NetworkGetAllStoriesRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : GetAllStoriesRepository {
    override suspend fun getAllStories(
        page: Int?, size: Int?, location: Int?
    ): Flow<Async<GetAllStoriesResponse>> {
        return networkDataSource.getAllStories(
            page = page, size = size, location = location
        ).map {
            Async.Success(it)
        }.catch<Async<GetAllStoriesResponse>> {
            Async.Error(R.string.universal_error_message)
        }
    }
}