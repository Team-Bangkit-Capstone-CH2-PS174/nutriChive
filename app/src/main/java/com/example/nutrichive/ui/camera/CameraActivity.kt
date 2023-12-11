package com.example.nutrichive.ui.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.nutrichive.R
import com.example.nutrichive.databinding.ActivityCameraBinding
import com.example.nutrichive.ui.reciperecomen.RecipeRecomenActivity
import com.example.nutrichive.utils.compressImage
import com.example.nutrichive.utils.getImageUri
import java.io.ByteArrayOutputStream

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backButton.setOnClickListener { finish() }
            btnGaleri.setOnClickListener { startGallery() }
            btnCamera.setOnClickListener { startCamera() }
        }
    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            currentImageUri = it
            processAndStartActivity()
        } ?: run {
            Toast.makeText(this, "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launchIntentCamera.launch(currentImageUri)
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { processAndStartActivity() }
        }
    }

    private fun processAndStartActivity() {
        currentImageUri?.let { uri ->
            val compressedUri = compressImage(this, uri, 500)
            if (compressedUri != null) {
                intentRecipeRecomenActivity(compressedUri)
            } else {
                Toast.makeText(this, "Gagal melakukan kompresi gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun intentRecipeRecomenActivity(imageUri: Uri) {
        val intent = Intent(this, RecipeRecomenActivity::class.java)
        intent.putExtra(EXTRA_IMAGE_URI, imageUri.toString())
        startActivity(intent)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}