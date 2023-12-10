package id.furqoncreative.jetstories.data.source.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("story_favorite")
data class StoryFavoriteItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("photoUrl")
    val photoUrl: String
)