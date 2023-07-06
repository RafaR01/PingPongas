package pt.ipt.dama.pingpongas

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
import android.provider.MediaStore.Audio.Media
import android.widget.Button
import android.widget.ImageView
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
}