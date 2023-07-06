package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName

/**
 * define the structure of data to send to API
 */
class SignUpResult (
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String
)