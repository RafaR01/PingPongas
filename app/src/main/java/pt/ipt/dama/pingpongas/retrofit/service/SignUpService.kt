package pt.ipt.dama.pingpongas.retrofit.service

import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.model.SignUpResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignUpService {

    /**
     * function to read data from API
     * transform data in JSON format to Kotlin objects
     */
    @GET("users/nao_interessa_a_ninguem")
    fun listUsers(): Call<List<SignUpData>>
    /**
     * function to write a new user to API
     */
    @POST("users/nao_interessa_a_ninguem")
    fun addUser(@Body data:SignUpData): Call<SignUpResult>
}