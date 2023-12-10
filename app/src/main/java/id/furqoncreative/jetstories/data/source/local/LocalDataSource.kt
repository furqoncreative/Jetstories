package id.furqoncreative.jetstories.data.source.local

import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    suspend fun addFavorite(storyFavoriteItem: StoryFavoriteItem)
    suspend fun deleteFavorite(storyFavoriteItem: StoryFavoriteItem)

    suspend fun getAllFavorites(): Flow<List<StoryFavoriteItem>>
    suspend fun isFavorite(storyId: String): Flow<Boolean>
}