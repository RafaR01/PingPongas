package pt.ipt.dama.pingpongas.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pt.ipt.dama.pingpongas.retrofit.service.SignUpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    // link to API
    private val host = "https://rafaelr2001.pythonanywhere.com/"

    // The GSON tool is the one that we use to convert JSON
    // read by Retrofit
    // the parameter .setLenient() should be used only at
    //     developer mode
    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // function that really use the retrofit service
    fun noteService():SignUpService=retrofit.create(SignUpService::class.java)
}