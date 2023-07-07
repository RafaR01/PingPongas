package pt.ipt.dama.pingpongas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pt.ipt.dama.pingpongas.R

class AdicionarPartida2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida2)

        val nomeJogador = findViewById<TextView>(R.id.nomeJog1);
        val nomeAdversario = findViewById<TextView>(R.id.nomeJog2);
        var btnAdicionarPartida : Button = findViewById(R.id.partida);

        val nomeadv = valorAdversario.advEscolhido;
        nomeAdversario.text = nomeadv.toString();

        btnAdicionarPartida.setOnClickListener{
            adicionaPartida();
        }
    }

    private fun adicionaPartida(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}