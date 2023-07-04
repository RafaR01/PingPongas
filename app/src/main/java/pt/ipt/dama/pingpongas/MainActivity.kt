package pt.ipt.dama.pingpongas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAdicionarPartida : Button = findViewById(R.id.addpartida);
        var btnRanking : Button = findViewById(R.id.ranking);
        var btnHistorico : Button = findViewById(R.id.historico);
        var btnPerfil : Button = findViewById(R.id.perfil);
        var btnTermSessao : Button = findViewById(R.id.termsessao);

        btnAdicionarPartida.setOnClickListener{
            adicionaPartida();
        }

        btnRanking.setOnClickListener{
            verRanking();
        }

        btnHistorico.setOnClickListener{
            verHistorico();
        }

        btnPerfil.setOnClickListener{
            verPerfil();
        }

        btnTermSessao.setOnClickListener{
            termSessao();
        }
    }

    private fun adicionaPartida(){

    }

    private fun verRanking(){

    }

    private fun verHistorico(){

    }

    private fun verPerfil(){

    }

    private fun termSessao(){

    }
}