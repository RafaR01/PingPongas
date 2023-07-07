package pt.ipt.dama.pingpongas.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class StatsData (
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("image") val image: String,
    @SerializedName("bestScore") val bestScore: BigInteger,
    @SerializedName("victoryChance") val victoryChance: Float,
    @SerializedName("winStreak") val winStreak: BigInteger,
    @SerializedName("bestWinStreak") val bestWinStreak: BigInteger,
    @SerializedName("gamesWon") val gamesWon: BigInteger,
    @SerializedName("gamesPlayed") val gamesPlayed: BigInteger,
    @SerializedName("score") val score: BigInteger
)