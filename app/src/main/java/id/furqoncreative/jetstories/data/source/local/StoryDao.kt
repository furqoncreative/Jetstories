package id.furqoncreative.jetstories.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(quote: List<StoryItem>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryItem>

    @Query("DELETE FROM story")
    suspend fun deleteAllStories()
}