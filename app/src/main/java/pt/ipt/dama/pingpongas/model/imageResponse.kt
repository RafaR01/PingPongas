package pt.ipt.dama.pingpongas.model

import okhttp3.MultipartBody

data class imageResponse(
    val image: MultipartBody.Part
)
