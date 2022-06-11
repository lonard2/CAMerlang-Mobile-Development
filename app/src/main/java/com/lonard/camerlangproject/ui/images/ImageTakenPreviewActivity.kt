package com.lonard.camerlangproject.ui.images

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.camera.CameraUtil
import com.lonard.camerlangproject.camera.CameraUtil.Companion.reduceFileImage
import com.lonard.camerlangproject.camera.ScannerCameraActivity
import com.lonard.camerlangproject.databinding.ActivityImageTakenPreviewBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.formatDateTime
import com.lonard.camerlangproject.formatPhotoDateTime
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.consultation.ConsultationDetailActivity
import java.io.File
import java.time.LocalDateTime
import java.util.*

class ImageTakenPreviewActivity : AppCompatActivity() {

    private lateinit var bind: ActivityImageTakenPreviewBinding

    private var retrievedImgFile: File? = null
    private val locale: String = Locale.getDefault().country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageTakenPreviewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var galleryImage: Uri? = intent.getParcelableExtra("image_gallery")
        var takenImage = intent.getSerializableExtra("imageCameraTaken") as File?
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
                cameraXIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                cameraLauncher.launch(cameraXIntent)
            }

            pictureSendBtn.setOnClickListener {
                Log.d(TAG, retrievedImgFile.toString())
                retrievedImgFile?.let { it1 -> saveConsultationToDb(it1) }
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

        retrievedImgFile = CameraUtil.fileCompressToFile(rotatedBitmap, takenImageFile)
    }

    private fun processImageFromGallery(galleryImage: Uri) {
        val convertedFile = galleryImage.let { CameraUtil.uriToFile(it, this@ImageTakenPreviewActivity) }

        retrievedImgFile = convertedFile
        bind.imageTakenPreviewContainer.setImageURI(galleryImage)
    }

    private fun saveConsultationToDb(acceptedImageFile: File) {
        if(retrievedImgFile != null) {
            val acceptedImage = reduceFileImage(acceptedImageFile)

            val consultFactory: ConsultationViewModelFactory =
                ConsultationViewModelFactory.getFactory(this)

            val consultViewModel: ConsultationViewModel by viewModels {
                consultFactory
            }

            val fileToUri = Uri.fromFile(acceptedImage)
            val currentDateTime = LocalDateTime.now().toString()
            val formattedCurrentDateTime = currentDateTime.formatPhotoDateTime()

            bind.apply {
                consultViewModel.addConsultationEntry(fileToUri.toString(), formattedCurrentDateTime)
                    .observe(this@ImageTakenPreviewActivity) { inclusionStatus ->

                    if (inclusionStatus != null) {
                        when (inclusionStatus) {
                            is DataLoadResult.Loading -> {
                                loadAnimLottie.visibility = View.VISIBLE
                            }

                            is DataLoadResult.Successful -> {
                                loadAnimLottie.visibility = View.GONE

                                val containedData = inclusionStatus.data

                                Snackbar.make(
                                    pictureSendBtn, when (locale) {
                                        "in" -> {
                                            "Kerja bagus! Fotomu berhasil disimpan, kamu akan dibawa ke halaman detail konsultasi."
                                        }
                                        "en" -> {
                                            "Great job! Your photo successfully saved in the database. You will be brought to the consultation detail page."
                                        }
                                        else -> {
                                            "Photo saving on database successfully done!."
                                        }
                                    }, Snackbar.LENGTH_LONG
                                ).show()

                                val consultationIntent = Intent(this@ImageTakenPreviewActivity, ConsultationDetailActivity::class.java)
                                consultationIntent.putExtra(ConsultationDetailActivity.EXTRA_CONSULTATION_DATA, containedData)

                                consultationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                                startActivity(consultationIntent,
                                    ActivityOptions.makeSceneTransitionAnimation(this@ImageTakenPreviewActivity).toBundle())
                            }

                            is DataLoadResult.Failed -> {
                                loadAnimLottie.visibility = View.GONE

                                Snackbar.make(
                                    pictureSendBtn, when (locale) {
                                        "in" -> {
                                            "Aduh, fotomu gagal disimpan di database... Silakan coba lagi ya."
                                        }
                                        "en" -> {
                                            "Ouch, your photo cannot be saved in the database. Please try again."
                                        }
                                        else -> {
                                            "Error in photo saving on database."
                                        }
                                    }, Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
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