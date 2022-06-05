package com.lonard.camerlangproject.ml

import android.content.Context
import android.graphics.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ObjectDetectionUtil {

    private lateinit var context: Context
    lateinit var resultBitmap: Bitmap

    private fun objectDetection(bitmap: Bitmap) {
        val retrievedTakenImage = TensorImage.fromBitmap(bitmap)

        val objectDetectionOptions = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(3)
            .setScoreThreshold(0.3f)
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
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)

            val tagSize = Rect(0, 0, 0, 0)

            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = 32F
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

//    private fun detectionProcess() {
//        val retrievedTakenImage = TensorImage.fromBitmap(bitmap)
//
//        val detectionModel = CamerlangTrainingModel.newInstance(context)
//
//        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 1, 1, 3), DataType.INT8)
//
//        inputFeature.loadBuffer(byteBuffer)
//
//        val outputModel = detectionModel.process(inputFeature)
//
//        val outputFeature0 = outputModel.outputFeature0AsTensorBuffer
//        val outputFeature1 = outputModel.outputFeature1AsTensorBuffer
//        val outputFeature2 = outputModel.outputFeature2AsTensorBuffer
//        val outputFeature3 = outputModel.outputFeature3AsTensorBuffer
//
//        val outputFeature4 = outputModel.outputFeature4AsTensorBuffer
//        val outputFeature5 = outputModel.outputFeature5AsTensorBuffer
//        val outputFeature6 = outputModel.outputFeature6AsTensorBuffer
//        val outputFeature7 = outputModel.outputFeature7AsTensorBuffer
//
//        detectionModel.close()
//    }

}