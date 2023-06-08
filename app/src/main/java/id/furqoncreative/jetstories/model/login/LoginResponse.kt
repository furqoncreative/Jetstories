package id.furqoncreative.jetstories.model.login


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("error") val error: Boolean?,
    @SerialName("loginResult") val loginResult: LoginResult?,
    @SerialName("message") val message: String?
)