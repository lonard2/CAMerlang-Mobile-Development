package com.lonard.camerlangproject

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = dateFormat.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun String.formatPhotoDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
    val date = dateFormat.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date) + " | " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date)
}