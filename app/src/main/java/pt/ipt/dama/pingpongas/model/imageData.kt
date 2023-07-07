package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

class imageData (
    //@SerializedName("imagem") val imagem: String,
    @Path("user_id") userId: Int,
    @Part imagem: MultipartBody.Part
)