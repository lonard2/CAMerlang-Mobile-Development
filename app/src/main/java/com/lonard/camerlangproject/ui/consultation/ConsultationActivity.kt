package com.lonard.camerlangproject.ui.consultation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityConsultationBinding

class ConsultationActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }
        }
    }
}