package pt.ipt.dama.pingpongas.retrofit.service

import okhttp3.MultipartBody
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.PontosData
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


    /**
     * Função que lê os dados dos utilizadores da API
     * e transforma a data de formato JSON para objetos Kotlin
     */
    @GET("users/nao_interessa_a_ninguem")
    fun listUsers(): Call<List<SignUpData>>

    /**
     * Função que vai autenticar os utilizadores
     */
    @GET("auth/{username}/{password}")
    fun authenticate(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<LoginData>

    /**
     * Função que lê os dados dos utilizadores da API
     * e transforma a data de formato JSON para objetos Kotlin
     */
    @GET("/stats/{user_id}/nao_interessa_a_ninguem")
    fun userStats(
        @Path("user_id") user_id: Int
    ): Call<StatsData>

    @GET("/stats/nao_interessa_a_ninguem")
    fun usersStats(): Call<List<StatsData>>

    /**
     * Função que lê os dados dos utilizadores da API
     * e transforma a data de formato JSON para objetos Kotlin
     */
    @GET("/users/{user_id}/nao_interessa_a_ninguem")
    fun userData(
        @Path("user_id") user_id: Int
    ): Call<SignUpData>

    /**
     * Função que escreve um utilizador na API
     */
    @POST("users/nao_interessa_a_ninguem")
    fun addUser(@Body data:SignUpData): Call<SignUpResult>

    /**
     * Função que permite enviar a fotografia de perfil
     */
    @Multipart
    @POST("upload/{user_id}/nao_interessa_a_ninguem")
    fun uploadImage(
        @Path("user_id") userId: Int,
        @Part imagem: MultipartBody.Part
    ): Call<SignUpData>

    /**
     * Função que envia o resultado do jogo juntamente com o username dos jogadores
     */
    @POST("/matches/nao_interessa_a_ninguem")
    fun sendPontosAPI(@Body data:PontosData): Call<PontosData>

    /**
     * Função que permite ir buscar a informação do utilizador consoante o seu ID
     */
    @GET("users/{user_id}/nao_interessa_a_ninguem")
    fun getUser( @Path("user_id") user_id: Int) : Call<SignUpData>

    /**
     * Função que permite ir buscar as partidas realizadas
     */
    @GET("/matches/nao_interessa_a_ninguem")
    fun getMatches(): Call<List<MatchData>>


}