package pt.ipt.dama.pingpongas.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipt.dama.pingpongas.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Obtain the Logged User Id, passed trough intent
        val loggedId = intent.getStringExtra("loggedId")
        val nomeJogador = intent.getStringExtra("loggeduser")

        /**
         * Inicialização dos botões que estão no ecrã principal
         */
        var btnAdicionarPartida : Button = findViewById(R.id.addpartida);
        var btnRanking : Button = findViewById(R.id.ranking);
        var btnHistorico : Button = findViewById(R.id.historico);
        var btnPerfil : Button = findViewById(R.id.perfil);
        var btnTermSessao : Button = findViewById(R.id.termsessao);

        val view = findViewById<View>(R.id.menu)
        view.setOnTouchListener { v, event -> // Disable slide gesture
            true
        }


        /**
         * Colocar os botões a funcionar quando
         * o utlizador toca neles
         */
        btnAdicionarPartida.setOnClickListener{
            adicionaPartida(nomeJogador);
        }

        btnRanking.setOnClickListener{
            verRanking(loggedId);
        }

        btnHistorico.setOnClickListener{
            verHistorico();
        }

        btnPerfil.setOnClickListener{
            verPerfil(loggedId);
        }


        btnTermSessao.setOnClickListener{
            termSessao();
        }
    }

    /**
     * Funcção adicionaPartida vai abrir um novo layout no qual
     * vai permitir o user selecionar o adversário
     */
    private fun adicionaPartida(nomeJogador: String?){
        val intent = Intent(this,AdicionarPartida::class.java)
        intent.putExtra("loggeduser", "$nomeJogador" )
        startActivity(intent)
    }

    /**
     * Função que vai abrir um novo layout no qual vai permitir
     * ver os rankings de todos os users da aplicação
     */
    private fun verRanking(loggedId: String?){
        val intent = Intent(this, Ranking::class.java)
        intent.putExtra("loggedId", "$loggedId")
        startActivity(intent)
    }

    /**
     * Função que vai abrir um novo layout que vai permitir
     * ao user poder ver o seu histórico
     */
    private fun verHistorico(){
        val intent = Intent(this, Historico::class.java)
        startActivity(intent)
    }

    /**
     * Função que vai abrir um novo layout no qual
     * permite ao utilizador utilizar a câmera para tirar uma foto de perfil
     */
    private fun verPerfil(loggedId: String?) {
        val intent = Intent(this, Perfil::class.java)
        intent.putExtra("loggedId", "$loggedId")
        startActivity(intent)
    }

    /**
     * Função que termina a sessão do user
     */
    private fun termSessao(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Disable the back button
        // You can leave this method empty to do nothing, or add your own logic here
    }

}