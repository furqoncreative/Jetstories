package id.furqoncreative.jetstories.model.stories


import com.google.gson.annotations.SerializedName

data class GetAllStoriesResponse(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("listStory") val listStory: List<Story?>?,
    @SerializedName("message") val message: String?
)