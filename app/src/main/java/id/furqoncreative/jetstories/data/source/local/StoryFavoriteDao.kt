package id.furqoncreative.jetstories.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storyFavoriteItem: StoryFavoriteItem)

    @Delete
    suspend fun delete(storyFavoriteItem: StoryFavoriteItem)

    @Query("SELECT * FROM story")
    suspend fun getAllFavorites(): List<StoryFavoriteItem>

    @Query("SELECT EXISTS(SELECT 1 FROM story_favorite WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}