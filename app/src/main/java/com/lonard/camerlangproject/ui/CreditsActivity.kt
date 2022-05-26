package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityCreditsBinding
import com.lonard.camerlangproject.databinding.ActivityFrontBinding

class CreditsActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCreditsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCreditsBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}