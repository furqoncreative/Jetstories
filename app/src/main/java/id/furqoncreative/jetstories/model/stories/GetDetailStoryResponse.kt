package id.furqoncreative.jetstories.model.stories


import com.google.gson.annotations.SerializedName

data class GetDetailStoryResponse(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("story") val story: Story?
)