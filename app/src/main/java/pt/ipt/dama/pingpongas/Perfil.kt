package pt.ipt.dama.pingpongas

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pt.ipt.dama.pingpongas.model.imageData
import pt.ipt.dama.pingpongas.model.imageResponse
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileDescriptor
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

        fotoUtilizador = findViewById(R.id.imagemPerfil);
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
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_CAPTURE_CODE -> {
                    val bitmap = uriToBipmap(imagemUri!!);
                    fotoUtilizador.setImageBitmap(bitmap);
                    val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                    val fileName = "IMG$timeStamp.jpg"
                    val userId = 9 // Replace with the actual user ID
                    val imageFile = File(getRealPathFromURI(imagemUri!!))
                    //val imagem = prepareImagePart("imagem", imageFile)
                    val requestBody = RequestBody.create(MediaType.parse("image/jpg"), imageFile)
                    val imagem = MultipartBody.Part.createFormData("imagem", imageFile.name, requestBody)
                    val data=imageData(9,imagem);
                    uploadImage(data){

                    }
                }

                RESULT_LOAD_IMAGE -> {
                    if (data != null) {
                        imagemUri = data.data;
                        val bitmap = uriToBipmap(imagemUri!!);
                        fotoUtilizador.setImageBitmap(bitmap);
                        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                        val fileName = "IMG$timeStamp.jpg"
                        val userId = 9 // Replace with the actual user ID
                        val imageFile = File(getRealPathFromURI(imagemUri!!))
                        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile)
                        val imagem = MultipartBody.Part.createFormData("imagem", fileName, requestBody)
                        val data=imageData(9,imagem);
                        uploadImage(data){

                        }
                    }
                }
            }
        }

       /**
        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK){
            val bitmap = uriToBipmap(imagemUri!!);
            fotoUtilizador?.setImageBitmap(bitmap);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data!=null){
            imagemUri = data.data;
            val bitmap = uriToBipmap(imagemUri!!);
            fotoUtilizador.setImageBitmap(bitmap)
        }*/
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

    private fun uploadImage(data: imageData, onResult:  (imageResponse?) -> Unit) {
        val call= RetrofitInitializer().noteService().uploadImage(data)
        call.enqueue(
            object: Callback<imageResponse> {
                /**
                 * Invoked for a received HTTP response.
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call [Response.isSuccessful] to determine if the response indicates success.
                 */
                override fun onResponse(call: Call<imageResponse>, response: Response<imageResponse>) {
                    val addedImage=response.body()
                    onResult(addedImage)
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected exception
                 * occurred creating the request or processing the response.
                 */
                override fun onFailure(call: Call<imageResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

            }
        )
    }
}