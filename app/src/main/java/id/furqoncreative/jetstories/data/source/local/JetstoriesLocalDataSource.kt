package id.furqoncreative.jetstories.data.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class JetstoriesLocalDataSource @Inject constructor(
    private val storyDatabase: StoryDatabase
) : LocalDataSource {
    override suspend fun addFavorite(storyFavoriteItem: StoryFavoriteItem) {
        storyDatabase.favoriteDao().insert(storyFavoriteItem)
    }

    override suspend fun deleteFavorite(storyFavoriteItem: StoryFavoriteItem) {
        storyDatabase.favoriteDao().delete(storyFavoriteItem)
    }

    override suspend fun getAllFavorites(): Flow<List<StoryFavoriteItem>> = flow {
        emit(storyDatabase.favoriteDao().getAllFavorites())
    }.flowOn(Dispatchers.IO)

    override suspend fun isFavorite(storyId: String): Flow<Boolean> = flow {
        emit(storyDatabase.favoriteDao().isFavorite(storyId))
    }.flowOn(Dispatchers.IO)

}