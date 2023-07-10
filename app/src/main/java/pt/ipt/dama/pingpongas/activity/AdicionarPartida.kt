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

        val loggedId = intent.getStringExtra("loggedId")
        //vai buscar o username da pessoa que se autenticou na aplicação
        val nomeJogador = intent.getStringExtra("loggeduser")

        // Chamar a função listUsers() para obter a lista de utilizadores
        val listaUtilizadores: List<String> = listUsers()

        // Criar o adapter com a lista de utilizadores
        utilizadoresAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUtilizadores)
        utilizadoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selecUtilizadores.adapter = utilizadoresAdapter

        //Vai esperar que o utilizador escolha o adversário e depois chama o criaPartida com o username e com o nome do adversario
        selecUtilizadores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedUser = parent.getItemAtPosition(position).toString()
                btnNovaPartida.setOnClickListener {
                    criaPartida(nomeJogador, username = selectedUser, loggedId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no user is selected
            }
        }


    }

    private fun listUsers(): List<String> {
        // Chama a função de API para obter a lista de uilizadores
        // Retorna a lista de utilizadores obtida
        val call = RetrofitInitializer().noteService().listUsers()
        var dataList : List<String> = mutableListOf<String>()
        val nomeJogador = intent.getStringExtra("loggeduser")

        call.enqueue(object : Callback<List<SignUpData>?> {
            override fun onResponse(
                call: Call<List<SignUpData>?>,
                response: Response<List<SignUpData>?>
            ){
                if (response.isSuccessful) {
                    val loginData = response.body()
                    if (loginData != null) {
                        val filteredList = loginData.filter { it.username != nomeJogador }
                        if(filteredList.isEmpty()){
                            Toast.makeText(this@AdicionarPartida, "Não há adversários disponiveis", Toast.LENGTH_LONG).show()
                        }
                        //coloca o nome dos utilizadores na lista
                        val dataList = filteredList.map { it.username }
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

    //botão no qual vai redirecionar para a view da partida onde passa o nome do utilizador que se autenticou e o do adversário
    private fun criaPartida(nomeJogador: String?, username: String, loggedId: String?) {
        val intent = Intent(this, AdicionarPartidas2::class.java)
        intent.putExtra("loggedId", "$loggedId")
        intent.putExtra("loggeduser", nomeJogador)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}