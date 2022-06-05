package com.lonard.camerlangproject.ml

import android.graphics.RectF

data class DetectionResult(
    val boundingBox: RectF,
    val text: String
)
