package com.lonard.camerlangproject.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.databinding.ActivitySplashBinding
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModel
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModelFactory
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref
import com.lonard.camerlangproject.ui.homepage.OnboardingActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "local_user_data")

class SplashActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySplashBinding

    private val animation = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        showSplashAnimation()

        val localUserStorage = LocalUser_pref.getLocalUserInstance(dataStore)

        val localViewModel = ViewModelProvider(
            this,
            LocalUserViewModelFactory(localUserStorage)
        )[LocalUserViewModel::class.java]

        localViewModel.getStartUp().observe(this) { setting ->

            if(!setting.firstRun) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val splashIntent = Intent(this@SplashActivity, FrontActivity::class.java)
                    startActivity(splashIntent)
                    finish()
                }, SPLASH_DELAY)
            }
            else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val onboardingIntent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    startActivity(onboardingIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                    finish()

                    localViewModel.disableFirstRun()
                }, SPLASH_DELAY)
            }
        }
    }

    private fun showSplashAnimation() {
        val logo = ObjectAnimator.ofFloat(bind.appLogo, View.ALPHA, 1f).setDuration(600)

        val wordmark = ObjectAnimator.ofFloat(bind.appWordmark, View.ALPHA, 1f).setDuration(1750)
        val slogan = ObjectAnimator.ofFloat(bind.appVersion, View.ALPHA, 1f).setDuration(1750)
        val version = ObjectAnimator.ofFloat(bind.appVersion, View.ALPHA, 1f).setDuration(1750)

        val otherThings = animation.apply {
            playTogether(wordmark, slogan, version)
        }

        animation.apply {
            playSequentially(logo, otherThings)
        }
    }

    companion object {
        const val SPLASH_DELAY = 3500L
    }

}