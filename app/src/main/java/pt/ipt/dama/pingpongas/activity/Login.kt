package  pt.ipt.dama.pingpongas.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val view = findViewById<View>(R.id.login)
        view.setOnTouchListener { v, event -> // Disable slide gesture
            true
        }

        val btnLogin: Button = findViewById(R.id.loginButton)

        btnLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextText).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword2).text.toString()


            authenticate(username,password)

        }

        val registerText = findViewById<TextView>(R.id.textView2)
        registerText.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set the color when the touch is initially pressed down
                    registerText.setTextColor(Color.parseColor("#6996C6")) // Change the color to your desired color
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Reset the color when the touch is released or canceled
                    registerText.setTextColor(Color.parseColor("#2990FF")) // Change the color to your original color
                }
            }
            // Return false to allow the event to continue to other listeners (e.g., click listener)
            false
        }
        registerText.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }


    /**
     * Função que vai autentitcar o utilizador e permitir utilizar a aplicação e
     * retorna o id do utilizador e o seu username
     */
    private fun authenticate(username: String, password: String) {
        val call = RetrofitInitializer().noteService().authenticate(username, password)

        call.enqueue(object : Callback<LoginData?> {
            override fun onResponse(call: Call<LoginData?>, response: Response<LoginData?>) {
                if (response.isSuccessful) {
                    val loginData = response.body()
                    if (loginData != null) {
                        // Authentication successful
                        val userId = loginData.id
                        Toast.makeText(this@Login, "Sessão iniciada com sucesso", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        intent.putExtra("loggedId", "$userId")
                        intent.putExtra("loggeduser", "$username")
                        startActivity(intent)
                    } else {
                        // Authentication failed (no matching user found)
                        Toast.makeText(this@Login, "Acesso negado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Login, "Acesso negado", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Login, Register::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<LoginData?>, t: Throwable) {
                // Handle network failure
                Toast.makeText(this@Login, "Erro de conexão", Toast.LENGTH_LONG).show()
                val intent = Intent(this@Login, Register::class.java)
                startActivity(intent)
                t.printStackTrace()
            }
        })
    }
    override fun onBackPressed() {
        // Disable the back button
        // You can leave this method empty to do nothing, or add your own logic here
    }
}