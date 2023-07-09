package pt.ipt.dama.pingpongas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import kotlinx.coroutines.selects.select
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdicionarPartida : AppCompatActivity() {

    private lateinit var selecUtilizadores: Spinner
    private lateinit var utilizadoresAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida)

        val btnNovaPartida: Button = findViewById(R.id.novapartida)
        selecUtilizadores = findViewById(R.id.selectadvr)

        val nomeJogador = intent.getStringExtra("loggeduser")

        // Chamar a função listUsers() para obter a lista de usuários
        val listaUtilizadores: List<String> = listUsers()

        // Criar o adapter com a lista de usuários
        utilizadoresAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUtilizadores)
        utilizadoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selecUtilizadores.adapter = utilizadoresAdapter

        selecUtilizadores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedUser = parent.getItemAtPosition(position).toString()
                // Do something with the selected user
                btnNovaPartida.setOnClickListener {
                    criaPartida(nomeJogador, username = selectedUser)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no user is selected
            }
        }


    }

    private fun listUsers(): List<String> {
        // Chame a função de API para obter a lista de usuários
        // Retorne a lista de usuários obtida
        val call = RetrofitInitializer().noteService().listUsers()
        var dataList : List<String> = mutableListOf<String>()

        call.enqueue(object : Callback<List<SignUpData>?> {
            override fun onResponse(
                call: Call<List<SignUpData>?>,
                response: Response<List<SignUpData>?>
            ){
                if (response.isSuccessful) {
                    val loginData = response.body()
                    if (loginData != null) {
                        val dataList = loginData.map { it.username }
                        utilizadoresAdapter.clear()
                        utilizadoresAdapter.addAll(dataList)
                        utilizadoresAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<SignUpData>?>, t: Throwable) {
                TODO("Not yet implemented")
                t.printStackTrace()
            }
        })
        return dataList
    }

    private fun criaPartida(nomeJogador: String?, username: String) {
        val intent = Intent(this, AdicionarPartidas2::class.java)
        intent.putExtra("loggeduser", nomeJogador)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}