package com.lonard.camerlangproject.ui.images

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lonard.camerlangproject.databinding.ActivityImageShowBinding
import com.lonard.camerlangproject.ui.consultation.ConsultationDetailActivity
import com.lonard.camerlangproject.ui.consultation.ConsultationDetailActivity.Companion.EXTRA_DIRECTED_FROM_PREVIEW_ACTIVITY
import com.squareup.picasso.Picasso

class ImageShowActivity : AppCompatActivity() {
    private lateinit var bind: ActivityImageShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageShowBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val sentFromPreviewActivity = intent.getBooleanExtra(DIRECTED_FROM_CONSULTATION_DETAIL, false)
        val sentFromInformativeSections = intent.getBooleanExtra(DIRECTED_FROM_INFORMATIVE_SECTIONS, false)

        bind.apply {
            backBtn.setOnClickListener {
                finishAfterTransition()
            }

            if(sentFromInformativeSections) {
                val imagePack = intent.getStringExtra(EXTRA_PIC)
                Glide.with(this@ImageShowActivity).load(imagePack).into(detailedImageClickContainer)
            }

            if(sentFromPreviewActivity) {
                val bitmapPack = intent.getParcelableExtra<Bitmap>(EXTRA_BITMAP)
                Glide.with(this@ImageShowActivity).load(bitmapPack).into(detailedImageClickContainer)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    companion object {
        const val EXTRA_PIC = "picture_link_url"
        const val EXTRA_BITMAP = "picture_bitmap"

        const val DIRECTED_FROM_CONSULTATION_DETAIL = "directed_from_consultation_detail"
        const val DIRECTED_FROM_INFORMATIVE_SECTIONS = "directed_from_library_or_article_piece"
    }
}