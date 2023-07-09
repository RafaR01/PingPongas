package pt.ipt.dama.pingpongas.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.LoginData
import pt.ipt.dama.pingpongas.model.SignUpData
import pt.ipt.dama.pingpongas.model.StatsData
import pt.ipt.dama.pingpongas.model.imageResponse
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Perfil : AppCompatActivity() {

    private lateinit var fotoUtilizador : ImageView
    private var imagemUri : Uri? = null;
    private val RESULT_LOAD_IMAGE = 123;
    private val IMAGE_CAPTURE_CODE = 654;
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Tag", "entrou");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Obtain the Logged User Id, passed trough intent
        val loggedId = intent.getStringExtra("loggedId")
        var loggedIdInt = loggedId!!.toInt()
        userStats(loggedIdInt)

        userData(loggedIdInt)

        val btnImagem : Button = findViewById(R.id.altImagem);
        val btnImagemGaleria : Button = findViewById(R.id.fotoGaleria);

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
        }

        btnImagemGaleria.setOnClickListener {
            fotoGaleria();
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                IMAGE_CAPTURE_CODE -> {
                    val bitmap = uriToBitmap(imagemUri!!)
                    //fotoUtilizador.setImageBitmap(bitmap)
                    val loggedId = intent.getStringExtra("loggedId")
                    var loggedIdInt = loggedId!!.toInt()
                    val userId = loggedIdInt // Replace with the actual user ID

                    val imageUrl = "https://rafaelr2001.pythonanywhere.com/images/$userId.jpg" // Replace with your image URL

                    val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                    val fileName = "IMG$timeStamp.jpg"
                    val imageFile = File(getRealPathFromURI(imagemUri!!))
                    uploadImage(userId, imageFile)
                    Picasso.get()
                        .invalidate(imageUrl)

                    Picasso.get()
                        .load(imageUrl)
                        .into(fotoUtilizador)
                }
                RESULT_LOAD_IMAGE -> {
                    if (data != null) {
                        val imagemUri = data.data
                        val bitmap = uriToBitmap(imagemUri!!)
                        //fotoUtilizador.setImageBitmap(bitmap)
                        val loggedId = intent.getStringExtra("loggedId")
                        var loggedIdInt = loggedId!!.toInt()
                        val userId = loggedIdInt // Replace with the actual user ID

                        val imageUrl = "https://rafaelr2001.pythonanywhere.com/images/$userId.jpg" // Replace with your image URL

                        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                        val fileName = "IMG$timeStamp.jpg"
                        val imageFile = File(getRealPathFromURI(imagemUri!!))
                        uploadImage(userId, imageFile)

                        Picasso.get()
                            .invalidate(imageUrl)

                        Picasso.get()
                            .load(imageFile)
                            .into(fotoUtilizador)
                    }
                }
            }
        }
    }

    private fun uriToBitmap(uri:Uri): Bitmap{
        return MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var filePath = ""
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(filePathColumn[0])
            filePath = it.getString(columnIndex)
            it.close()
        }
        return filePath
    }

    private fun fotoGaleria(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE)
    }

    fun uploadImage(userId: Int, imageFile: File) {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("imagem", imageFile.name, requestFile)

        val call: Call<SignUpData> = RetrofitInitializer().noteService().uploadImage(userId, body)
        call.enqueue(object : Callback<SignUpData> {
            override fun onResponse(call: Call<SignUpData>, response: Response<SignUpData>) {
                if (response.isSuccessful) {
                    val loggedId = intent.getStringExtra("loggedId")
                    val imageUrl = "https://rafaelr2001.pythonanywhere.com/images/$loggedId.jpg"
                    Picasso.get()
                        .invalidate(imageUrl)

                    Picasso.get()
                        .load(imageFile)
                        .into(fotoUtilizador)
                    Toast.makeText(this@Perfil, "Imagem Carregada", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this@Perfil, "Ocorreu um erro", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SignUpData>, t: Throwable) {
                Log.d("tag", t.message.toString())
                Toast.makeText(this@Perfil, "Erro de conexão", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun userData(userId: Int){
        val call = RetrofitInitializer().noteService().userData(userId)

        call.enqueue(object : Callback<SignUpData?> {
            override fun onResponse(call: Call<SignUpData?>, response: Response<SignUpData?>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    if (userData != null) {
                        // Authentication successful

                        fotoUtilizador = findViewById(R.id.imagemPerfil);

                        val imageUrl = "https://rafaelr2001.pythonanywhere.com/images/${userData.image}" // Replace with your image URL

                        Picasso.get()
                            .load(imageUrl)
                            .networkPolicy(NetworkPolicy.NO_STORE)
                            .into(fotoUtilizador)

                        Toast.makeText(this@Perfil, "Dados Obtidos", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@Perfil, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Perfil, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SignUpData?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /*
    * Stats
    * */
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

                        var quadradoVictoryChance = findViewById<TextView>(R.id.textView10)

                        if(victoryChance > 60)
                            quadradoVictoryChance.setBackgroundColor(Color.parseColor("#24F08D"))

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
                        intent.putExtra("loggedId", "$userId")
                    } else {
                        // Authentication failed (no matching user found)
                        Toast.makeText(this@Perfil, "Ocorreu um erro", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Perfil, "Ocorreu um erro", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<StatsData?>, t: Throwable) {
                // Handle network failure
                Log.d("tag",t.message.toString())
                Toast.makeText(this@Perfil, "Erro de conexão", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}