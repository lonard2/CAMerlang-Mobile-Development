package com.lonard.camerlangproject.ui.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityImageShowBinding
import com.squareup.picasso.Picasso

class ImageShowActivity : AppCompatActivity() {
    private lateinit var bind: ActivityImageShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageShowBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            val imagePack = intent.getStringExtra(EXTRA_PIC)

            Picasso.get().load(imagePack).into(detailedImageClickContainer)
        }
    }

    companion object {
        const val EXTRA_PIC = "picture_link_url"
    }
}