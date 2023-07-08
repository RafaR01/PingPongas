package pt.ipt.dama.pingpongas.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

/**
 * class to represent the data structure,
 * obtained from API
*/
class SignUpData (
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("image") val image: String
)