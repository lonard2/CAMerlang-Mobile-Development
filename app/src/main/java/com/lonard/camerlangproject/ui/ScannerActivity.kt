package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding
import com.lonard.camerlangproject.databinding.ActivityScannerBinding

class ScannerActivity : AppCompatActivity() {
    private lateinit var bind: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}