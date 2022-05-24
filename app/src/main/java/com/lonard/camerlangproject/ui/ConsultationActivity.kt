package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.databinding.ActivityConsultationBinding

class ConsultationActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}