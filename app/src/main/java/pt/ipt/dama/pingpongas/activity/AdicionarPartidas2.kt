package pt.ipt.dama.pingpongas.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.PontosData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.absoluteValue

class AdicionarPartidas2 : AppCompatActivity() {

    private lateinit var pontosJogador1view: EditText
    private lateinit var pontosJogador2view: EditText
    private lateinit var btnAdicionarPartida: Button
    private lateinit var nomeJogadorTextView : TextView
    private lateinit var nomeAdversarioTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida2)

        nomeJogadorTextView= findViewById(R.id.nomeJog1)
        nomeAdversarioTextView = findViewById(R.id.nomeJog2)
        btnAdicionarPartida = findViewById(R.id.partida)
        pontosJogador1view = findViewById(R.id.pontosUser1)
        pontosJogador2view = findViewById(R.id.pontosUser2)
        val errorMessage = findViewById<TextView>(R.id.errorMessage)

        /**
         * Coloca o nome do utilizador que se autenticou e o do adversário nas variáveis
         * nomeJogador e nomeAdversario respetivamente
         */
        val nomeJogador = intent.getStringExtra("loggeduser")
        val nomeAdversario = intent.getStringExtra("username")

        /**
         * Mostra no ecrã o nome dos jogadores da partida
         */
        nomeJogadorTextView.text = nomeJogador
        nomeAdversarioTextView.text = nomeAdversario
        btnAdicionarPartida.setOnClickListener {
            pontosPartida(errorMessage)
            adicionarPartida(nomeJogador)
        }


    }

    /**
     * Faz as verificações dos pontos e se estiver correto chama a função
     * sendPontosAPI no qual envia os pontos e os respetivos nomes dos utilizadores
     * que estiveram a jogar
     */
    private fun pontosPartida(errorMessage: TextView) {
        val nomeJogador = intent.getStringExtra("loggeduser")
        val nomeAdversario = intent.getStringExtra("username")
        val pontosJogador1Text = pontosJogador1view.text.toString().trim()
        val pontosJogador2Text = pontosJogador2view.text.toString().trim()

        if(pontosJogador1Text.isEmpty() || pontosJogador2Text.isEmpty()){
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Por favor, preencha todos os campos de pontos."
        }

        val pontosJogador1 = pontosJogador1Text.toInt()
        val pontosJogador2 = pontosJogador2Text.toInt()


        if (pontosJogador1 < 11 && pontosJogador2 < 11) {
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Resultado inválido. Para ser considerado válido, um jogador precisa ter pelo menos 11 pontos."
            pontosPartida(errorMessage)
        }
        if(pontosJogador1 == 11 && pontosJogador2 < 10){
            val pontuacao = PontosData("$nomeJogador", "$nomeAdversario", pontosJogador1, pontosJogador2)
            sendPontosAPI(pontuacao)
        }
        if(pontosJogador1 < 10 && pontosJogador2 ==11){
            val pontuacao = PontosData("$nomeJogador", "$nomeAdversario", pontosJogador1, pontosJogador2)
            sendPontosAPI(pontuacao)
        }
        if(pontosJogador1 ==  pontosJogador2){
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = "Resultado inválido. O jogo não pode acabar empatado."
            pontosPartida(errorMessage)
        }
        if(pontosJogador1 >= 10 && pontosJogador2 >=10){
            val diferenca = pontosJogador1-pontosJogador2;
            if(diferenca.absoluteValue == 2){
                if(pontosJogador1 > pontosJogador2) {
                    //jogador1 ganha
                    val pontuacao = PontosData("$nomeJogador", "$nomeAdversario", pontosJogador1, pontosJogador2)
                    sendPontosAPI(pontuacao)
                }else {
                    //jogador2 ganha
                    val pontuacao = PontosData("$nomeJogador", "$nomeAdversario", pontosJogador1, pontosJogador2)
                    sendPontosAPI(pontuacao)
                }
            }
            else{
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = "Resultado inválido. O jogo só pode acabar com a diferença de 2 pontos."
                pontosPartida(errorMessage)
            }
        }
    }

    /**
     * Volta para o menu inicial e envia o nome do utilizador autenticado
     */
    private fun adicionarPartida(nomeJogador: String?){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("loggeduser", "$nomeJogador")
        startActivity(intent)
    }

    /**
     * Envia os pontos para a API e se tiver sucesso mostra a mensagem no ecrã
     * se falhar mostra a mensagem respectiva à falha
     */
    private fun sendPontosAPI(pontosData: PontosData){
        val call= RetrofitInitializer().noteService().sendPontosAPI(pontosData)
        call.enqueue(
            object: Callback<PontosData> {
                /**
                 * Invoked for a received HTTP response.
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call [Response.isSuccessful] to determine if the response indicates success.
                 */
                override fun onResponse(call: Call<PontosData>, response: Response<PontosData>) {
                    val pontosData=response.body()
                    Toast.makeText(this@AdicionarPartidas2, "Pontos enviados com sucesso", Toast.LENGTH_LONG).show()
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected exception
                 * occurred creating the request or processing the response.
                 */
                override fun onFailure(call: Call<PontosData>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@AdicionarPartidas2, "Erro conexão", Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}