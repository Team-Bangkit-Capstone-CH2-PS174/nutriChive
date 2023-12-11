package com.example.nutrichive.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import android.provider.MediaStore
import com.example.nutrichive.BuildConfig
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.io.File
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun getImageUri(context: Context): Uri {
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
        }
        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
    return uri ?: getImageUriForPreQ(context)
}

private fun getImageUriForPreQ(context: Context): Uri {
    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        imageFile
    )
}

fun downsampleBitmap(context: Context, imageUri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeStream(inputStream, null, options)
    inputStream?.close()

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
    options.inJustDecodeBounds = false

    return context.contentResolver.openInputStream(imageUri)?.use {
        BitmapFactory.decodeStream(it, null, options)
    }
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun compressImage(context: Context, imageUri: Uri, maxSizeKB: Int): Uri? {
    val downsampledBitmap = downsampleBitmap(context, imageUri, 224, 224) // Sesuaikan dengan kebutuhan resolusi yang diinginkan
    downsampledBitmap?.let {
        val compressedUri = saveCompressedImage(context, it, maxSizeKB)
        it.recycle()
        return compressedUri
    }
    return null
}

private fun saveCompressedImage(context: Context, bitmap: Bitmap, maxSizeKB: Int): Uri? {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
    var quality = 80

    while (outputStream.size() / 1024 > maxSizeKB && quality > 0) {
        outputStream.reset()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        quality -= 10
    }

    val fileName = "compressed_image.jpg"
    context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
        it.write(outputStream.toByteArray())
    }

    return Uri.fromFile(context.getFileStreamPath(fileName))
}