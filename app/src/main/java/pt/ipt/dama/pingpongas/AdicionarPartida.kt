package pt.ipt.dama.pingpongas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast



class valorAdversario{
    companion object{
        var advEscolhido : String = "";
    }
}
class AdicionarPartida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida)

        /**
         * Inicialização do botão e os elementos da selected list
         */
        var btnNovaPartida : Button = findViewById(R.id.novapartida);
        val selecUtilizadores = findViewById<Spinner>(R.id.selectadvr);
        val listaUtilizadores = resources.getStringArray(R.array.adversarios);

        btnNovaPartida.setOnClickListener {
            criaPartida();
        }

        if(selecUtilizadores != null) {
            val utilizadores =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUtilizadores);
            utilizadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selecUtilizadores.adapter = utilizadores;
        }

        fun mostraValor(view : View){
            Toast.makeText(this, "Seleciona Adversário " + selecUtilizadores.selectedItem.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private fun criaPartida(){
        val intent = Intent(this, AdicionarPartida2::class.java)
        startActivity(intent)
    }
}