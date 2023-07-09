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


class valorAdversario{
    companion object{
        var advEscolhido : String = "";
    }
}
class AdicionarPartida : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida)

        val btnNovaPartida: Button = findViewById(R.id.novapartida)
        val selecUtilizadores: Spinner = findViewById(R.id.selectadvr)
        val listaUtilizadores = resources.getStringArray(R.array.adversarios)

        val nomeJogador = intent.getStringExtra("loggedUsername")

        val utilizadoresAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUtilizadores)
        utilizadoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selecUtilizadores.adapter = utilizadoresAdapter

        selecUtilizadores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedUser = parent.getItemAtPosition(position).toString()
                // Do something with the selected user
                criaPartida(nomeJogador,username = selectedUser)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no user is selected
            }
        }

        btnNovaPartida.setOnClickListener {
            val selectedUser = selecUtilizadores.selectedItem.toString()
            criaPartida(nomeJogador,username = selectedUser)
        }
    }

    private fun criaPartida(nomeJogador: String?,username: String) {
        val intent = Intent(this, AdicionarPartidas2::class.java)
        intent.putExtra("loggedUsername", "$nomeJogador")
        intent.putExtra("username", username)
        startActivity(intent)
    }
}