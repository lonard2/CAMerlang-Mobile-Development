package com.lonard.camerlangproject.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lonard.camerlangproject.databinding.ActivityCreditsBinding

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