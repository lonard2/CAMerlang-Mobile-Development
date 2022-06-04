package com.lonard.camerlangproject.ui.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityImageShowBinding
import com.lonard.camerlangproject.databinding.ActivityImageTakenPreviewBinding
import com.squareup.picasso.Picasso

class ImageTakenPreviewActivity : AppCompatActivity() {

    private lateinit var bind: ActivityImageTakenPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageTakenPreviewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
                finish()
            }


        }
    }
}