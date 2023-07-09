package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName

class MatchData (
    @SerializedName("id") val id: Int,
    @SerializedName("perdedor") val perdedor: String,
    @SerializedName("vencedor") val vencedor: String,
    @SerializedName("perdedor_id") val perdedor_id: Int,
    @SerializedName("vencedor_id") val vencedor_id : Int,
    @SerializedName("perdedor_image") val perdedor_image: String,
    @SerializedName("vencedor_image") val vencedor_image: String,
    @SerializedName("scoreVencedor") val scoreVencedor: Int,
    @SerializedName("scorePerdedor") val scorePerdedor: Int
)