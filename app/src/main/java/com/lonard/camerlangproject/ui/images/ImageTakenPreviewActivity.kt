package com.lonard.camerlangproject.ui.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.camera.CameraUtil
import com.lonard.camerlangproject.databinding.ActivityImageShowBinding
import com.lonard.camerlangproject.databinding.ActivityImageTakenPreviewBinding
import com.lonard.camerlangproject.ui.FrontActivity
import com.squareup.picasso.Picasso
import java.io.File

class ImageTakenPreviewActivity : AppCompatActivity() {

    private lateinit var bind: ActivityImageTakenPreviewBinding
    private var retrieveImageFile: File? = null

    private var imageUri: Uri? = null
    private var imageUriGallery: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageTakenPreviewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val galleryImage = intent.getParcelableExtra<Uri>("image_gallery")
        val takenImage = intent.getParcelableExtra<>()

        bind.apply {
            if(result) {
                processImageFromGallery()
            }

            backBtn.setOnClickListener {
                finish()
                finish()
            }

            retakeBtn.setOnClickListener {
                cameraLauncher.launch(imageUri)
            }

        }
    }

    private fun processImageUri() {

    }

    private fun processImageFromGallery() {
        val convertedFile = galleryImage?.let { CameraUtil.uriToFile(it, this@ImageTakenPreviewActivity) }

        retrieveImageFile = convertedFile

        bind.imageTakenPreviewContainer.setImageURI(galleryImage)
    }

    companion object {
        const val TAG = "Preview Image Activity"

        const val CAMERAX_RESPONSE_CODE = 100
        const val GALLERY_RESPONSE_CODE = 200
    }
}