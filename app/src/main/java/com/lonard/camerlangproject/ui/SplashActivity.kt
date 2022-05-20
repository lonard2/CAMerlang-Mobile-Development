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
import com.lonard.camerlangproject.databinding.ActivitySplashBinding
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "local_user_data")

class SplashActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val localUserStorage = LocalUser_pref.getLocalUserInstance(dataStore)

        Handler(Looper.getMainLooper()).postDelayed({
            val splashIntent = Intent(this@SplashActivity, HomepageFragment::class.java)
            startActivity(splashIntent)
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        const val SPLASH_DELAY = 5000L
    }
}