package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName

class PontosData (
    @SerializedName("player1_username") val player1_username : String,
    @SerializedName("player2_username") val player2_username : String,
    @SerializedName("player1_points") val player1_points : Int,
    @SerializedName("player2_points") val player2_points : Int
)
