package com.lonard.camerlangproject.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.camera.CameraUtil.Companion.createFile
import com.lonard.camerlangproject.camera.CameraUtil.Companion.rotateBitmap
import com.lonard.camerlangproject.databinding.ActivityScannerCameraBinding
import com.lonard.camerlangproject.ui.FrontActivity.Companion.CAMERAX_RESPONSE_CODE
import java.io.File

class ScannerCameraActivity : AppCompatActivity() {

    private lateinit var bind: ActivityScannerCameraBinding

    private var imgCapture: ImageCapture? = null
    private var modeSelect: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityScannerCameraBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.cameraModeSwitch.setOnClickListener {
            modeSelect = if (modeSelect == CameraSelector.DEFAULT_BACK_CAMERA)
                CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
        }

        bind.pictureTakeBtn.setOnClickListener {
            takePicture()
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

    private fun takePicture() {
        val imgCapture = imgCapture ?: return

        val imgFile = createFile(application)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imgFile).build()

        imgCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(this),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Snackbar.make(
                        bind.scannerViewfinder,
                        "Bagus, gambarmu telah diperoleh dan akan segera dianalisis kecenderungan permasalahan kulitnya.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    val intent = Intent()

                    intent.putExtra("image", imgFile)
                    intent.putExtra("isBackCamera", modeSelect == CameraSelector.DEFAULT_BACK_CAMERA)
                    setResult(StoryAddActivity.CAMERAX_RESPONSE_CODE, intent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {

                }

            }
        )

    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == CAMERAX_RESPONSE_CODE) {
            val imageFile = result.data?.getSerializableExtra("image") as File
            val cameraTypeCheck = result.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val bitmapResult: Bitmap = rotateBitmap(BitmapFactory.decodeFile(imageFile.path), cameraTypeCheck)

            bind.setImageBitmap(bitmapResult)

            getImageFile = rotateBitmap(bitmapResult, imageFile)
        }
    }


}