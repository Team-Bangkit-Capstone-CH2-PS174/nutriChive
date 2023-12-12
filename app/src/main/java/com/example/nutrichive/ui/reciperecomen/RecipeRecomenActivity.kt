package com.example.nutrichive.ui.reciperecomen

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.nutrichive.databinding.ActivityRecipeRecomenBinding
import com.example.nutrichive.ml.ModelV2
import com.example.nutrichive.ui.camera.CameraActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.DequantizeOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class RecipeRecomenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeRecomenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeRecomenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageUriString = intent.getStringExtra(CameraActivity.EXTRA_IMAGE_URI)
        val imageUri = Uri.parse(imageUriString)

        binding.imageDetail.setImageURI(imageUri)

        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        predictImage(bitmap)
    }

    private fun predictImage(bitmap: Bitmap) {
        val labels = application.assets.open("label.txt").bufferedReader().readLines()

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(DequantizeOp(0.0f, 1/255.0f))
            .build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        tensorImage = imageProcessor.process(tensorImage)

        val model = ModelV2.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxIdx] < fl){
                maxIdx = index
            }
        }

        binding.titleBahan.text = labels[maxIdx]

        model.close()
    }
}