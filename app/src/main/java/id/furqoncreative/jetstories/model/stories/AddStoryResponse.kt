package id.furqoncreative.jetstories.model.stories


import com.google.gson.annotations.SerializedName

data class AddStoryResponse(
    @SerializedName("error") val error: Boolean, @SerializedName("message") val message: String?
)