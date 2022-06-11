package com.lonard.camerlangproject.ui.consultation

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.ml.DetectionResult
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.squareup.picasso.Picasso
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ConsultationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val consultationParcel = intent.getParcelableExtra<ConsultationItemEntity>(
            EXTRA_CONSULTATION_DATA
        ) as ConsultationItemEntity

        val consultFactory: ConsultationViewModelFactory = ConsultationViewModelFactory.getFactory(this@ConsultationDetailActivity)
        val consultViewModel: ConsultationViewModel by viewModels {
            consultFactory
        }

        bind.apply {

            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(consultationParcel.consultationImg).into(consultationTakenImage)

            consultationIdDetail.text = getString(R.string.consultation_id_format, consultationParcel.id.toString())
            consultationDatetimeDetail.text = consultationParcel.processedAt

            val consultationTakenImageOnUri = Uri.parse(consultationParcel.consultationImg)

            val detectionResult = objectDetection(consultationTakenImageOnUri, applicationContext,
                consultViewModel, consultationIdDetail.toString())

            consultationTakenImage.setImageBitmap(detectionResult)

            consultationTakenImage.setOnClickListener {
                val sharedAnim =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@ConsultationDetailActivity,
                        Pair(bind.consultationTakenImage, "zoomed_image"),
                    )

                val viewZoomedImg = Intent(this@ConsultationDetailActivity, ImageShowActivity::class.java)
                viewZoomedImg.putExtra(ImageShowActivity.EXTRA_PIC, consultationParcel.consultationImg)

                startActivity(viewZoomedImg, sharedAnim.toBundle())
            }

//            consultViewModel.retrieveConsultationDetections().observe(this@ConsultationDetailActivity) { detections ->
//                showDetectionResults(detections)
//            }
        }
    }

//    private fun showDetectionResults(detectionItems: List<ConsultationDetectionItemEntity>) {
//        bind.consultationOutcomeCard.layoutManager = LinearLayoutManager(this@ConsultationDetailActivity,
//            LinearLayoutManager.VERTICAL, false)
//
//        val consultHistoryAdapter = ConsultationDetailAdapter(detectionItems as ArrayList<ConsultationDetectionItemEntity>)
//        bind.consultationOutcomeCard.adapter = consultHistoryAdapter
//    }

    private fun objectDetection(imageUri: Uri, context: Context,
                                consultViewModel: ConsultationViewModel,
                                consultationId: String): Bitmap {

        val resultBitmap: Bitmap

        val boundingBoxTypeface: Typeface = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
        val boundingBoxTypefaceColor = ContextCompat.getColor(context, R.color.primary_color_logo)
        val boundingBoxColor = ContextCompat.getColor(context, R.color.secondary_color_logo)

        val bitmap: Bitmap
        val contentResolver: ContentResolver = context.contentResolver

        bitmap = if(Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        } else {
            val imgSource = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(imgSource)
        }

        val retrievedTakenImage = TensorImage.fromBitmap(bitmap)

        val objectDetectionOptions = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(8)
            .setScoreThreshold(0.4f)
            .build()

        val detector = ObjectDetector.createFromFileAndOptions(
            context,
            "efficientdet-lite0-camerlang-v4.tflite",
            objectDetectionOptions
        )

        val detectionResults = detector.detect(retrievedTakenImage)

        val displayResults = detectionResults.map { detection ->
            val category = detection.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"

            consultViewModel.addDetectionResultData(consultationId, category.label, category.score.times(100).toInt())

            DetectionResult(detection.boundingBox, text)
        }

        val imgWithResult = drawDetectionResult(bitmap, displayResults,
            boundingBoxColor, boundingBoxTypefaceColor, boundingBoxTypeface)

        resultBitmap = imgWithResult

        return resultBitmap
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
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)

            val tagSize = Rect(0, 0, 0, 0)

            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = boundingBoxTypefaceColor
            pen.strokeWidth = 2F

            pen.typeface = boundingBoxTypeface

            pen.textSize = 24F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_consultation_information"
    }
}