package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityLibraryHomeBinding
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var bind: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}