package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityConsultationHistoryBinding
import com.lonard.camerlangproject.databinding.ActivityFrontBinding

class FrontActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFrontBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}