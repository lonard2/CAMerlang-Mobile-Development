package com.lonard.camerlangproject.camera

import android.app.Activity
import android.app.ActivityOptions
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.camera.CameraUtil.Companion.uriToFile
import com.lonard.camerlangproject.databinding.ActivityScannerCameraBinding
import com.lonard.camerlangproject.ui.images.ImageTakenPreviewActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ScannerCameraActivity : AppCompatActivity() {

    private lateinit var bind: ActivityScannerCameraBinding

    private var imgCapture: ImageCapture? = null
    private var modeSelect: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var retrievedImgFile: File? = null
    private val locale: String = Locale.getDefault().country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityScannerCameraBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val dateStamp: String = SimpleDateFormat(
            DATE_FORMAT,
            Locale.US
        ).format(System.currentTimeMillis())

        val timeStamp: String = SimpleDateFormat(
            TIME_FORMAT,
            Locale.US
        ).format(System.currentTimeMillis())

        bind.apply {
            cameraModeSwitch.setOnClickListener {
                modeSelect = if (modeSelect == CameraSelector.DEFAULT_BACK_CAMERA)
                    CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                    provideCamera()
            }

            pictureTakeBtn.setOnClickListener {
                takePicture(dateStamp, timeStamp)
            }

            galleryGetBtn.setOnClickListener {
                goToGallery()
            }
        }

    }

    override fun onResume() {
        super.onResume()

        provideCamera()
    }

    private fun provideCamera() {
        val camProviderBlueprint = ProcessCameraProvider.getInstance(this)

        camProviderBlueprint.addListener({
            val provider: ProcessCameraProvider = camProviderBlueprint.get()

            val surfacePreview = Preview.Builder()
                .build()
                .also { surface ->
                    surface.setSurfaceProvider(bind.scannerViewfinder.surfaceProvider)
                }

            imgCapture = ImageCapture.Builder().build()

            try {
                provider.unbindAll()

                provider.bindToLifecycle(
                    this,
                    modeSelect,
                    surfacePreview,
                    imgCapture
                )
            } catch (e: Exception) {
                Snackbar.make(bind.scannerViewfinder,
                    "Mohon maaf, kamera sedang tidak dapat diaktifkan. Coba lagi beberapa waktu.",
                    Snackbar.LENGTH_LONG).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture(
        dateStamp: String,
        timeStamp: String
    ) {
        val imgCapture = imgCapture ?: return

        val imgName = "CAMerlang-consultation-scan-img-$dateStamp-$timeStamp"

        val imgValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imgName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imgValue
        ).build()

        imgCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(this),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Snackbar.make(
                        bind.scannerViewfinder,
                        "Gambarmu telah berhasil diperoleh! Silakan cek fotomu untuk pengecekan. " +
                                "Jika kamu memilih untuk mengirimkannya, kamu bisa .",
                        Snackbar.LENGTH_LONG
                    ).show()

                    val previewIntent = Intent(this@ScannerCameraActivity, ImageTakenPreviewActivity::class.java)

                    previewIntent.putExtra("imageCameraTaken", outputFileResults.savedUri)
                    setResult(ImageTakenPreviewActivity.CAMERAX_RESPONSE_CODE, previewIntent)

                    startActivity(previewIntent, ActivityOptions.makeSceneTransitionAnimation(
                        this@ScannerCameraActivity
                    ).toBundle())
                }

                override fun onError(exception: ImageCaptureException) {
                    Snackbar.make(
                        bind.scannerViewfinder,
                        "Gambarmu gagal diambil :( Silakan coba lagi ya. ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun goToGallery() {
        val galleryIntent = Intent()

        galleryIntent.action = ACTION_GET_CONTENT
        galleryIntent.type = "image/*"

        val chooserMenu = Intent.createChooser(galleryIntent,
            when (locale) {
                "id" -> "Pilihlah sebuah foto yang ingin dianalisis..."
                "en" -> "Select a photograph to be analyzed..."
                else -> "Photo selection"
            }
        )

        launchGallery.launch(chooserMenu)
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val convertedFile = uriToFile(selectedImg, this@ScannerCameraActivity)

            retrievedImgFile = convertedFile

            val sendIntent = Intent(this@ScannerCameraActivity, ImageTakenPreviewActivity::class.java)
            sendIntent.putExtra("image_gallery", selectedImg)

            sendIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            setResult(ImageTakenPreviewActivity.GALLERY_RESPONSE_CODE, sendIntent)
            startActivity(sendIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    companion object {
        const val TAG = "Camera scanner activity"

        const val DATE_FORMAT = "dd-MMM-yyyy"
        const val TIME_FORMAT = "hh-mm-ss"
    }

}