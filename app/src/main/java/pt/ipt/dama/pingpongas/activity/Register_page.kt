package pt.ipt.dama.pingpongas.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.model.SignUpResult
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.GregorianCalendar
import kotlin.random.Random


class register_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val btnRegister: Button = findViewById(R.id.button)

        btnRegister.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextText4).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
            val name = findViewById<EditText>(R.id.editTextText3).text.toString()
            val username = findViewById<EditText>(R.id.editTextText5).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.editTextTextPassword3).text.toString()

            if(validations(name, username, email, password, confirmPassword)){
                addNewUser(name, username, email, password)
                val intent = Intent(this, login_page::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Dados inválidos",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        // Add your email validation logic here
        // For example, you can use a regular expression pattern to validate the email format
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return email.matches(emailRegex)
    }

    fun isValidPassword(password: String): Boolean {
        // Add your password validation logic here
        // For example, you can check the password length and complexity
        return password.length >= 8
    }

    private fun validations(name: String, username: String, email: String, password: String, confirmPassword: String): Boolean{
        //Perform necessary data validation
        if (email == null || username == null || name == null || password == null || confirmPassword == null) {
            // Display an error message to the user indicating the required fields are not filled
            return false;
        }

        if (!isValidEmail(email)) {
            // Display an error message to the user indicating an invalid email format
            return false;
        }

        if (!isValidPassword(password)) {
            // Display an error message to the user indicating password requirements (e.g., length, complexity)
            return false;
        }

        if (!password.equals(confirmPassword)) {
            // Display an error message to the user indicating that the passwords do not match
            return false;
        }

        return true;
    }

    /**
     * add a random user to API
     */
    private fun addNewUser(name: String, username: String, email: String, password: String) {
        val user =  SignUpData(email, username, name, password)

        addUser(user){
            Toast.makeText(this,"Conta criada com sucesso",Toast.LENGTH_LONG).show()
        }
    }

    /**
     * function that really add the new user to the API
     */
    private fun addUser(user: SignUpData, onResult:  (SignUpResult?) -> Unit) {
        val call= RetrofitInitializer().noteService().addUser(user)
        call.enqueue(
            object: Callback<SignUpResult> {
                /**
                 * Invoked for a received HTTP response.
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call [Response.isSuccessful] to determine if the response indicates success.
                 */
                override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                    val addedUser=response.body()
                    onResult(addedUser)
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected exception
                 * occurred creating the request or processing the response.
                 */
                override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

            }
        )
    }
}