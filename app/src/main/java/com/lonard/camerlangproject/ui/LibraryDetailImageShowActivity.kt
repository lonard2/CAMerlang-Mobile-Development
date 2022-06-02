package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailImageShowBinding
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding
import com.lonard.camerlangproject.db.library.LibraryDetailImgEntity
import com.squareup.picasso.Picasso

class LibraryDetailImageShowActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailImageShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLibraryDetailImageShowBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            val imagePack = intent.getParcelableExtra<LibraryDetailImgEntity>(EXTRA_PIC) as LibraryDetailImgEntity

            Picasso.get().load(imagePack.pictureLink).into(detailedImageClickContainer)
        }
    }

    companion object {
        const val EXTRA_PIC = "picture_link_url"
    }
}