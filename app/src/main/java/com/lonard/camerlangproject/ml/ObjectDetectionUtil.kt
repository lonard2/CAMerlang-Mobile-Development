package com.lonard.camerlangproject.ml

import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.lonard.camerlangproject.R
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ObjectDetectionUtil {

    companion object {
        private lateinit var context: Context
        private lateinit var resultBitmap: Bitmap

        private val boundingBoxTypeface: Typeface = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
        private val boundingBoxTypefaceColor = ContextCompat.getColor(context, R.color.primary_color_logo)
        private val boundingBoxColor = ContextCompat.getColor(context, R.color.secondary_color_logo)

        private lateinit var bitmap: Bitmap
        private lateinit var contentResolver: ContentResolver

        fun objectDetection(imageUri: Uri): Bitmap {

            try {
                bitmap = if(Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                } else {
                    val imgSource = ImageDecoder.createSource(contentResolver, imageUri)
                    ImageDecoder.decodeBitmap(imgSource)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val retrievedTakenImage = TensorImage.fromBitmap(bitmap)

            val objectDetectionOptions = ObjectDetector.ObjectDetectorOptions.builder()
                .setMaxResults(8)
                .setScoreThreshold(0.4f)
                .build()

            val detector = ObjectDetector.createFromFileAndOptions(
                context,
                "CAMerlang_training_model.tflite",
                objectDetectionOptions
            )

            val detectionResults = detector.detect(retrievedTakenImage)

            val displayResults = detectionResults.map { detection ->
                val category = detection.categories.first()
                val text = "${category.label}, ${category.score.times(100).toInt()}%"

                DetectionResult(detection.boundingBox, text)
            }

            val imgWithResult = drawDetectionResult(bitmap, displayResults)
            resultBitmap = imgWithResult

            return resultBitmap
        }

        private fun drawDetectionResult(
            bitmap: Bitmap,
            detectionResults: List<DetectionResult>
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
    }

}