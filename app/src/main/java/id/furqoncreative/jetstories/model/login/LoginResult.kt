package id.furqoncreative.jetstories.model.login


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    @SerialName("name") val name: String?,
    @SerialName("token") val token: String?,
    @SerialName("userId") val userId: String?
)