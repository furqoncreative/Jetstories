package id.furqoncreative.jetstories.data.source.network

import id.furqoncreative.jetstories.model.login.LoginResponse
import id.furqoncreative.jetstories.model.register.RegisterResponse
import id.furqoncreative.jetstories.model.stories.GetAllStoriesResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface JetstoriesApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String, @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int?,
    ): GetAllStoriesResponse
}