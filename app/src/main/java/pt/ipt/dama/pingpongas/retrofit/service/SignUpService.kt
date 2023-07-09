package pt.ipt.dama.pingpongas.retrofit.service

import okhttp3.MultipartBody
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.MatchData
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.model.SignUpResult
import pt.ipt.dama.pingpongas.model.imageData
import pt.ipt.dama.pingpongas.model.imageResponse
import pt.ipt.dama.pingpongas.model.StatsData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SignUpService {

    @GET("auth/{username}/{password}")
    fun authenticate(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<LoginData>

    /**
     * function to read data from API
     * transform data in JSON format to Kotlin objects
     */
    @GET("/stats/{user_id}/nao_interessa_a_ninguem")
    fun userStats(
        @Path("user_id") user_id: Int
    ): Call<StatsData>

    @GET("/stats/nao_interessa_a_ninguem")
    fun usersStats(): Call<List<StatsData>>

    @GET("/matches/nao_interessa_a_ninguem")
    fun getMatches(): Call<List<MatchData>>

    /**
     * function to read data from API
     * transform data in JSON format to Kotlin objects
     */
    @GET("/users/{user_id}/nao_interessa_a_ninguem")
    fun userData(
        @Path("user_id") user_id: Int
    ): Call<SignUpData>

    /**
     * function to write a new user to API
     */
    @POST("users/nao_interessa_a_ninguem")
    fun addUser(@Body data:SignUpData): Call<SignUpResult>

    @Multipart
    @POST("upload/{user_id}/nao_interessa_a_ninguem")
    fun uploadImage(
        @Path("user_id") userId: Int,
        @Part imagem: MultipartBody.Part
    ): Call<SignUpData>

}