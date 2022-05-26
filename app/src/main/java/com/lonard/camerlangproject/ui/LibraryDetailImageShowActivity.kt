package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailImageShowBinding
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding

class LibraryDetailImageShowActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailImageShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLibraryDetailImageShowBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}