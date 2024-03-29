package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.network.NetworkDataSource
import id.furqoncreative.jetstories.model.stories.AddStoryResponse
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

interface AddStoryRepository {
    suspend fun addStory(
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Double? = 0.0,
        longitude: Double? = 0.0,
    ): Flow<Async<AddStoryResponse>>
}

@Singleton
class NetworkAddStoryRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : AddStoryRepository {
    override suspend fun addStory(
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: Double?,
        longitude: Double?
    ): Flow<Async<AddStoryResponse>> {

        return networkDataSource.addStory(
            file = file,
            description = description,
            latitude = latitude,
            longitude = longitude
        ).map {
            Async.Success(it)
        }.catch<Async<AddStoryResponse>> { throwable ->
            throwable.message?.let { Async.Error(it) }
        }
    }
}