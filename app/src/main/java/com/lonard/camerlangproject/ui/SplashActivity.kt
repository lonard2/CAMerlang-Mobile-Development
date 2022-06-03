package com.lonard.camerlangproject.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

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
                    val splashIntent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    startActivity(splashIntent)

                    localViewModel.disableFirstRun()

                    finish()
                }, SPLASH_DELAY)
            }
        }
    }

    companion object {
        const val SPLASH_DELAY = 5000L
    }
}