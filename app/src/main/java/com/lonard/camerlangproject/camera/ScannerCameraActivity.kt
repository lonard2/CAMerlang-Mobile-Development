package com.lonard.camerlangproject.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.databinding.ActivityScannerCameraBinding

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


}