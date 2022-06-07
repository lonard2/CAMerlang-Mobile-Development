package com.lonard.camerlangproject.ui.images

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.camera.CameraUtil
import com.lonard.camerlangproject.camera.CameraUtil.Companion.reduceFileImage
import com.lonard.camerlangproject.camera.ScannerCameraActivity
import com.lonard.camerlangproject.databinding.ActivityImageShowBinding
import com.lonard.camerlangproject.databinding.ActivityImageTakenPreviewBinding
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.FrontActivity
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class ImageTakenPreviewActivity : AppCompatActivity() {

    private lateinit var bind: ActivityImageTakenPreviewBinding

    private var retrievedImgFile: File? = null
    private val locale: String = Locale.getDefault().country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageTakenPreviewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var galleryImage: Uri? = intent.getParcelableExtra("imageCameraTaken")
        var takenImage = intent.getSerializableExtra("imageFile") as File?
        val backCamera = intent.getBooleanExtra("isSetBackCam", true)

        bind.apply {

            takenImage?.let { processImageFromCameraX(it, backCamera) }

            galleryImage?.let { processImageFromGallery(it) }

            backBtn.setOnClickListener {
                finish()
                finish()
            }

            retakeBtn.setOnClickListener {
                galleryImage = null
                takenImage = null

                val cameraXIntent = Intent(this@ImageTakenPreviewActivity,
                    ScannerCameraActivity::class.java)

                cameraLauncher.launch(cameraXIntent)
            }

            pictureSendBtn.setOnClickListener {
                retrievedImgFile?.let { it1 -> uploadPictureToApi(it1) }
            }

        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when(result.resultCode) {
            CAMERAX_RESPONSE_CODE -> {
                val recentTakenImageFile = result.data?.getSerializableExtra("imageFile") as File?
                val recentTakenCameraType = result.data?.getBooleanExtra("isSetBackCam", true)

                if (recentTakenImageFile != null) {
                    if (recentTakenCameraType != null) {
                        processImageFromCameraX(recentTakenImageFile, recentTakenCameraType)
                    }
                }
            }
            else -> {
                finish()
                Log.e(ScannerCameraActivity.TAG, "Image processing error!")
            }
        }
    }

    private fun processImageFromCameraX(takenImageFile: File, cameraType: Boolean) {
        val rotatedBitmap: Bitmap =
            CameraUtil.rotateBitmap(BitmapFactory.decodeFile(takenImageFile.path), cameraType)

        bind.imageTakenPreviewContainer.setImageBitmap(rotatedBitmap)

        retrievedImgFile = CameraUtil.fileRotateFromBitmap(rotatedBitmap, takenImageFile)
    }

    private fun processImageFromGallery(galleryImage: Uri) {
        val convertedFile = galleryImage.let { CameraUtil.uriToFile(it, this@ImageTakenPreviewActivity) }

        retrievedImgFile = convertedFile
        bind.imageTakenPreviewContainer.setImageURI(galleryImage)
    }

    private fun uploadPictureToApi(acceptedImageFile: File) {
        if(retrievedImgFile != null) {
            val acceptedImage = reduceFileImage(acceptedImageFile)

            val consultFactory: ConsultationViewModelFactory =
                ConsultationViewModelFactory.getFactory(this)

            val consultViewModel: ConsultationViewModel by viewModels {
                consultFactory
            }

            val requestImage = acceptedImage.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part =
                MultipartBody.Part.createFormData(
                    "acceptedPhoto", // check again, in the API maybe (acceptedPhoto???)
                    acceptedImage.name,
                    requestImage
                )

            consultViewModel.uploadStatus.observe(this) {

                finish()
                finish()
            }
        } else {
            Log.e(TAG, "Photo is missing!")
            Snackbar.make(bind.pictureSendBtn,
                when (locale) {
                    "id" -> {
                        "Fotomu masih kosong ya! " +
                                "Kemungkinan besar aplikasi ini bermasalah, silakan laporkan ke tim CAMerlang!"
                    }
                    "en" -> {
                        "Your photo is missing... " +
                                "Most likely this application have problems, please report it to CAMerlang team!"
                    }
                    else -> "Photo missing!"
                }, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TAG = "Preview Image Activity"

        const val CAMERAX_RESPONSE_CODE = 100
        const val GALLERY_RESPONSE_CODE = 200
    }
}