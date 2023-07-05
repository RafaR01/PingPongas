package pt.ipt.dama.pingpongas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdicionarPartida2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_partida2)

        var btnAdicionarPartida : Button = findViewById(R.id.partida);

        btnAdicionarPartida.setOnClickListener{
            adicionaPartida();
        }
    }

    private fun adicionaPartida(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}