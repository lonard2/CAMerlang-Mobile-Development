package com.lonard.camerlangproject.ui.homepage

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityOnboardingBinding
import com.lonard.camerlangproject.ui.FrontActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var bind: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_onboarding)

        bind.goToHomeBtn.setOnClickListener {
            val goToHomeIntent = Intent(this@OnboardingActivity, FrontActivity::class.java)
            startActivity(goToHomeIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}