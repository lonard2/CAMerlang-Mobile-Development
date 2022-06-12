package com.lonard.camerlangproject.ui.homepage

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityOnboardingBinding
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModel
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModelFactory
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref
import com.lonard.camerlangproject.ui.FrontActivity
import com.lonard.camerlangproject.ui.dataStore
import com.lonard.camerlangproject.ui.settings.SettingsMainFragment.Companion.TAG

class OnboardingActivity : AppCompatActivity() {

    private lateinit var bind: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val localUserStorage = LocalUser_pref.getLocalUserInstance(dataStore)

        val localViewModel = ViewModelProvider(
            this,
            LocalUserViewModelFactory(localUserStorage)
        )[LocalUserViewModel::class.java]

        bind.goToHomeBtn.setOnClickListener {
            val goToHomeIntent = Intent(this@OnboardingActivity, FrontActivity::class.java)
            startActivity(goToHomeIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

            localViewModel.disableFirstRun()
        }
    }
}