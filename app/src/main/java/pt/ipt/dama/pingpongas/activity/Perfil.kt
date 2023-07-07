package pt.ipt.dama.pingpongas.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.StatsData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileDescriptor
import java.io.IOException

class Perfil : AppCompatActivity() {

    private lateinit var fotoUtilizador : ImageView
    private var imagemUri : Uri? = null;
    private val RESULT_LOAD_IMAGE = 123;
    private val IMAGE_CAPTURE_CODE = 654;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Obtain the Logged User Id, passed trough intent
        val loggedId = intent.getStringExtra("loggedId")
        var loggedIdInt = loggedId!!.toInt()
        userStats(loggedIdInt)

        fotoUtilizador = findViewById(R.id.imagemPerfil);
        val btnImagem : Button = findViewById(R.id.altImagem);

        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            requestPermissions(permission, 112)
        }

        btnImagem.setOnClickListener {
            abrirCamera();
            true;
        }
    }

    private fun abrirCamera(){
        val values = ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "nova foto");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem da câmera");
        imagemUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagemUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    private fun uriToBipmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri,"r");
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor;
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        }catch (e: IOException){
            return null;
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK){
            val bitmap = uriToBipmap(imagemUri!!);
            fotoUtilizador?.setImageBitmap(bitmap);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data!=null){
            imagemUri = data.data;
            val bitmap = uriToBipmap(imagemUri!!);
            fotoUtilizador.setImageBitmap(bitmap)
        }
    }

    private fun userStats(user_id : Int) {
        val call = RetrofitInitializer().noteService().userStats(user_id)

        call.enqueue(object : Callback<StatsData?> {
            override fun onResponse(call: Call<StatsData?>, response: Response<StatsData?>) {
                if (response.isSuccessful) {
                    val statsData = response.body()
                    if (statsData != null) {
                        // Authentication successful
                        val userId = statsData.user_id
                        val userMatches = statsData.gamesPlayed
                        val bestWinStreak = statsData.bestWinStreak
                        val winStreak = statsData.winStreak
                        val gamesWon = statsData.gamesWon
                        val victoryChance = statsData.victoryChance
                        val gamesPlayed = statsData.gamesPlayed
                        val bestScore = statsData.bestScore
                        var valor = findViewById<TextView>(R.id.textView4)
                        valor.text = userId.toString()

                        var valor2 = findViewById<TextView>(R.id.textView9)
                        valor2.text = userMatches.toString()

                        var valor3 = findViewById<TextView>(R.id.textView19)
                        valor3.text = bestWinStreak.toString()

                        var valor4 = findViewById<TextView>(R.id.textView16)
                        valor4.text = winStreak.toString()

                        var valor5 = findViewById<TextView>(R.id.textView14)
                        valor5.text = gamesWon.toString()

                        var valor6 = findViewById<TextView>(R.id.textView12)
                        valor6.text = victoryChance.toString()

                        var valor7 = findViewById<TextView>(R.id.textView9)
                        valor7.text = gamesPlayed.toString()

                        var valor8 = findViewById<TextView>(R.id.textView4)
                        valor8.text = bestScore.toString()

                        Toast.makeText(this@Perfil, "Stats obitdos com sucesso", Toast.LENGTH_LONG).show()
//                        val intent = Intent(this@Perfil, MainActivity::class.java)
//                        startActivity(intent)
                        intent.putExtra("loggedId", "$userId")
                    } else {
                        // Authentication failed (no matching user found)
                        Toast.makeText(this@Perfil, "Eish", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Perfil, "Eish", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@Perfil, register_page::class.java)
//                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<StatsData?>, t: Throwable) {
                // Handle network failure
                Log.d("tag",t.message.toString())
                Toast.makeText(this@Perfil, "Erro de conexão", Toast.LENGTH_LONG).show()
//                val intent = Intent(this@Perfil, register_page::class.java)
//                startActivity(intent)
                t.printStackTrace()
            }
        })
    }
}