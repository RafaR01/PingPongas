package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName

/**
 * define the structure of data to send to API
 */
class LoginData (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)