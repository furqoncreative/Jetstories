package id.furqoncreative.jetstories.data.source.network.response

import id.furqoncreative.jetstories.model.login.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface JetstoriesApiService {

    @FormUrlEncoded
    @POST
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}