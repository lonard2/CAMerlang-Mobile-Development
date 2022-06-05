package com.lonard.camerlangproject.ml

import android.content.Context
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ObjectDetectionUtil {

    private lateinit var context: Context

    private fun detectionProcess() {
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