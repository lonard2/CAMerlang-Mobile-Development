package com.lonard.camerlangproject.ui.settings

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityCreditsBinding
import com.lonard.camerlangproject.ui.FrontActivity

class CreditsActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCreditsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCreditsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finishAfterTransition()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishAfterTransition()
    }
}