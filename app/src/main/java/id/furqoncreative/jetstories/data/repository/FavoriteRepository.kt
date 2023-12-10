package id.furqoncreative.jetstories.data.repository

import id.furqoncreative.jetstories.data.source.local.LocalDataSource
import id.furqoncreative.jetstories.data.source.mapper.toStory
import id.furqoncreative.jetstories.data.source.mapper.toStoryFavoriteItem
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteRepository {
    suspend fun addFavorite(story: Story)
    suspend fun deleteFavorite(story: Story)

    suspend fun getAllFavorites(): Flow<Async<List<Story>>>
    suspend fun isFavorite(storyId: String): Flow<Async<Boolean>>
}

class LocalFavoriteRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : FavoriteRepository {
    override suspend fun addFavorite(story: Story) {
        localDataSource.addFavorite(story.toStoryFavoriteItem())
    }

    override suspend fun deleteFavorite(story: Story) {
        localDataSource.deleteFavorite(story.toStoryFavoriteItem())
    }

    override suspend fun getAllFavorites(): Flow<Async<List<Story>>> =
        localDataSource.getAllFavorites()
            .map { storyItemList ->
                Async.Success(storyItemList.map { it.toStory() })
            }.catch {
                it.message?.let { it1 -> Async.Error(it1) }
            }

    override suspend fun isFavorite(storyId: String): Flow<Async<Boolean>> =
        localDataSource.isFavorite(storyId)
            .map {
                Async.Success(it)
            }.catch {
                it.message?.let { it1 -> Async.Error(it1) }
            }

}