package com.lonard.camerlangproject.ui.consultation

import android.app.ActivityOptions
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.db.consultation.DetectionResultEntity
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModel
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModelFactory
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref
import com.lonard.camerlangproject.ml.DetectionResult
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.FrontActivity
import com.lonard.camerlangproject.ui.dataStore
import com.lonard.camerlangproject.ui.rv_adapter.ConsultationDetailAdapter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.util.*
import kotlin.collections.ArrayList

class ConsultationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationDetailBinding

    private val locale: String = Locale.getDefault().language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val sentFromPreviewActivity = intent.getBooleanExtra(EXTRA_DIRECTED_FROM_PREVIEW_ACTIVITY, false)

        val localPref = LocalUser_pref.getLocalUserInstance(dataStore)

        val localViewModel = ViewModelProvider(
            this,
            LocalUserViewModelFactory(localPref)
        )[LocalUserViewModel::class.java]

        localViewModel.getStartUp().observe(this) { appSetting ->
            if (appSetting.darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val consultationParcel = intent.getParcelableExtra<ConsultationItemEntity>(
            EXTRA_CONSULTATION_DATA
        ) as ConsultationItemEntity

        val consultFactory: ConsultationViewModelFactory = ConsultationViewModelFactory.getFactory(this@ConsultationDetailActivity)
        val consultViewModel: ConsultationViewModel by viewModels {
            consultFactory
        }

        bind.apply {

            if(sentFromPreviewActivity) {
                backBtn.setOnClickListener {
                    val backIntent = Intent(this@ConsultationDetailActivity, FrontActivity::class.java)

                    backIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(backIntent, ActivityOptions.makeSceneTransitionAnimation(this@ConsultationDetailActivity).toBundle())

                    finishAfterTransition()
                }
            } else {
                backBtn.setOnClickListener {
                    finishAfterTransition()
                }
            }

            consultationIdDetail.text = getString(R.string.consultation_id_format, consultationParcel.id.toString())
            consultationDatetimeDetail.text = consultationParcel.processedAt

            val consultationTakenImageOnUri = Uri.parse(consultationParcel.consultationImg)

            val bitmap: Bitmap
            val contentResolver: ContentResolver = contentResolver

            bitmap = if(Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, consultationTakenImageOnUri)
            } else {
                val imgSource = ImageDecoder.createSource(contentResolver, consultationTakenImageOnUri)
                ImageDecoder.decodeBitmap(imgSource)
            }

            val bitmap8888 = rgbaBitmap(bitmap)
            val results = objectDetection(bitmap8888, applicationContext, consultViewModel, consultationParcel.id)

            runOnUiThread {
                bind.consultationTakenImage.setImageBitmap(results)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val sentFromPreviewActivity = intent.getBooleanExtra(EXTRA_DIRECTED_FROM_PREVIEW_ACTIVITY, false)

        bind.apply {
            if(sentFromPreviewActivity) {
                backBtn.setOnClickListener {
                    val backIntent = Intent(this@ConsultationDetailActivity, FrontActivity::class.java)

                    backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(backIntent, ActivityOptions.makeSceneTransitionAnimation(this@ConsultationDetailActivity).toBundle())
                }
            } else {
                backBtn.setOnClickListener {
                    finishAfterTransition()
                }
            }
        }

    }

    private fun rgbaBitmap(bitmap: Bitmap): Bitmap {
        return bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }

    private fun showDetectionResults(detectionItems: List<DetectionResultEntity>) {
        bind.consultationOutcomeCard.layoutManager = LinearLayoutManager(this@ConsultationDetailActivity,
            LinearLayoutManager.VERTICAL, false)

        val consultHistoryAdapter = ConsultationDetailAdapter(detectionItems as ArrayList<DetectionResultEntity>)
        bind.consultationOutcomeCard.adapter = consultHistoryAdapter
    }

    private fun objectDetection(bitmap: Bitmap, context: Context,
                                consultViewModel: ConsultationViewModel,
                                consultId: Int): Bitmap {

        val boundingBoxTypeface: Typeface = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
        val boundingBoxTypefaceColor = ContextCompat.getColor(context, R.color.primary_color_logo)
        val boundingBoxColor = ContextCompat.getColor(context, R.color.secondary_color_logo)

        val retrievedTakenImage = TensorImage.fromBitmap(bitmap)

        val objectDetectionOptions = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(8)
            .setScoreThreshold(0.4f)
            .build()

        val detector = ObjectDetector.createFromFileAndOptions(
            context,
            "camerlang-efficientdet-lite2.tflite",
            objectDetectionOptions
        )

        val detectionResults = detector.detect(retrievedTakenImage)

        val displayResults = detectionResults.map { detection ->
            val category = detection.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"
            DetectionResult(detection.boundingBox, text)
        }

        detectionResults.map { results ->
            val category = results.categories.first()
            Log.d("Consultation Detail Activity", category.toString())
            consultViewModel.addDetectionResultData(consultId, category.label, category.score.times(100).toInt()).observe(this) { detections ->
                if (detections != null) {
                    when (detections) {
                        is DataLoadResult.Loading -> {
                            bind.loadFrame.visibility = View.VISIBLE
                            bind.loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            Log.d("Consultation Detail Activity", detections.data.toString())
                            showDetectionResults(detections.data)

                            bind.loadFrame.visibility = View.GONE
                            bind.loadAnimLottie.visibility = View.GONE
                        }

                        is DataLoadResult.Failed -> {
                            bind.loadFrame.visibility = View.GONE
                            bind.loadAnimLottie.visibility = View.GONE

                            Snackbar.make(
                                bind.consultationOutcomeCard, when (locale) {
                                    "in" -> {
                                        "Aduh, data deteksi gambar ini tidak bisa ditampilkan. Silakan coba lagi ya."
                                    }
                                    "en" -> {
                                        "Ouch, this picture's detection data cannot be shown to you. Please try again."
                                    }
                                    else -> {
                                        "Error in detection data retrieval."
                                    }
                                }, Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        val imgWithResult = drawDetectionResult(bitmap, displayResults,
            boundingBoxColor, boundingBoxTypefaceColor, boundingBoxTypeface)

        return imgWithResult
    }

    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>,
        boundingBoxColor: Int,
        boundingBoxTypefaceColor: Int,
        boundingBoxTypeface: Typeface,
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            pen.color = boundingBoxColor
            pen.strokeWidth = 40F
          
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)

            val tagSize = Rect(0, 0, 0, 0)

            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = boundingBoxTypefaceColor
            pen.strokeWidth = 3F

            pen.typeface = boundingBoxTypeface

            pen.textSize = 80F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 5F) margin = 5F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_consultation_information"
        const val EXTRA_DIRECTED_FROM_PREVIEW_ACTIVITY = "from_previous_activity_is_preview_activity"
    }
}