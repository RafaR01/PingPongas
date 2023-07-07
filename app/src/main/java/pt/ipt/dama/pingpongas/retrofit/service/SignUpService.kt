package pt.ipt.dama.pingpongas.retrofit.service

import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.model.SignUpResult
import pt.ipt.dama.pingpongas.model.imageData
import pt.ipt.dama.pingpongas.model.imageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SignUpService {

    /**
     * function to read data from API
     * transform data in JSON format to Kotlin objects
     */
    @GET("users/nao_interessa_a_ninguem")
    fun listUsers(): Call<List<SignUpData>>

    @GET("auth/{username}/{password}")
    fun authenticate(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<LoginData>

    /**
     * function to write a new user to API
     */
    @POST("users/nao_interessa_a_ninguem")
    fun addUser(@Body data:SignUpData): Call<SignUpResult>

    @Multipart
    @POST("upload/9/nao_interessa_a_ninguem")
    fun uploadImage(@Part("imagem") imagePart: imageData): Call<imageResponse>


}