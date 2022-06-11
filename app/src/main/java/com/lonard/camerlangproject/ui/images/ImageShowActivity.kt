package com.lonard.camerlangproject.ui.images

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
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
            val bitmapPack = intent.getParcelableExtra<Bitmap>(EXTRA_BITMAP)

            imagePack?.let { Picasso.get().load(it).into(detailedImageClickContainer) }

            bitmapPack?.let { Glide.with(this@ImageShowActivity).load(bitmapPack).into(detailedImageClickContainer) }

        }
    }

    companion object {
        const val EXTRA_PIC = "picture_link_url"
        const val EXTRA_BITMAP = "picture_bitmap"
    }
}