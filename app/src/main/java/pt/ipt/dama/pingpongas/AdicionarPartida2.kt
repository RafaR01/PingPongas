package pt.ipt.dama.pingpongas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

class AdicionarPartida2 : AppCompatActivity() {

    private lateinit var pontosJogador1view: EditText
    private lateinit var pontosJogador2view: EditText
    private lateinit var btnAdicionarPartida: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida2)

        var nomeJogador = findViewById<TextView>(R.id.nomeJog1)
        var nomeAdversario = findViewById<TextView>(R.id.nomeJog2)
        btnAdicionarPartida = findViewById(R.id.partida)
        pontosJogador1view = findViewById(R.id.pontosUser1)
        pontosJogador2view = findViewById(R.id.pontosUser2)
        val errorMessage = findViewById<TextView>(R.id.errorMessage)

        val nomeadv = valorAdversario.advEscolhido
        nomeAdversario.text = nomeadv.toString()
        btnAdicionarPartida.setOnClickListener {
            pontosPartida(errorMessage)
        }


    }

    private fun pontosPartida(errorMessage: TextView) {
        val pontosJogador1Text = pontosJogador1view.text.toString().trim()
        val pontosJogador2Text = pontosJogador2view.text.toString().trim()
        var winner = 0;
        var loser = 0;

        if(pontosJogador1Text.isEmpty() || pontosJogador2Text.isEmpty()){
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Por favor, preencha todos os campos de pontos."
        }

        val pontosJogador1 = pontosJogador1Text.toInt()
        val pontosJogador2 = pontosJogador2Text.toInt()


        if (pontosJogador1 < 11 && pontosJogador2 < 11) {
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Resultado inválido. Para ser considerado válido, um jogador precisa ter pelo menos 11 pontos."
        }
        if(pontosJogador1 == 11 && pontosJogador2 < 10){
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Resultado inválido. Para ser considerado válido, um jogador precisa ter pelo menos 11 pontos."
            //adicionarPartida();
        }
        if(pontosJogador1 ==  pontosJogador2){
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Resultado inválido. O jogo não pode acabar empatado."
        }
        if(pontosJogador1 >= 11 && pontosJogador2 >=11){
            val diferenca = pontosJogador1-pontosJogador2;
            if(diferenca.absoluteValue == 2){
                if(pontosJogador1 > pontosJogador2) {
                    //jogador1 ganha
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "Jogador 1 ganhou."
                }else {
                    //jogador2 ganha
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "Jogador 2 ganhou."
                }
            }
            else{
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = "Resultado inválido. O jogo só pode acabar com a diferença de 2 pontos."
                pontosPartida(errorMessage)
            }
        }
    }


    private fun adicionarPartida(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

