package com.lonard.camerlangproject.camera

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.lonard.camerlangproject.R
import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

private const val DATE_FORMAT = "dd-MMM-yyyy"
private const val TIME_FORMAT = "hh:mm:ss.SSS"

class CameraUtil {
    companion object {
        private val dateStamp: String = SimpleDateFormat(
            DATE_FORMAT,
            Locale.US
        ).format(System.currentTimeMillis())

        private val timeStamp: String = SimpleDateFormat(
            TIME_FORMAT,
            Locale.US
        ).format(System.currentTimeMillis())

        fun createCustomTempFile(context: Context): File {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile("CAMerlang-consultation-img-$dateStamp-$timeStamp", ".png", storageDir)
        }

        fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
            val matrix = Matrix()
            return if (isBackCamera) {
                matrix.postRotate(90f)
                Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                )
            } else {
                matrix.postRotate(-90f)
                matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
                Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                )
            }
        }

        fun uriToFile(selectedImg: Uri, context: Context): File {
            val contentResolver: ContentResolver = context.contentResolver
            val myFile = createCustomTempFile(context)

            val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
            val outputStream: OutputStream = FileOutputStream(myFile)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()

            return myFile
        }

        fun rotateFileImage(file: File, isBackCamera: Boolean): File {
            val bitmap = BitmapFactory.decodeFile(file.path)

            val rotatedBitmap = rotateBitmap(bitmap, isBackCamera)

            var compressQuality = 100
            var streamLength: Int

            do {
                val bmpStream = ByteArrayOutputStream()
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
                compressQuality -= 5
            } while (streamLength > 1000000)

            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

            return file
        }
    }
}