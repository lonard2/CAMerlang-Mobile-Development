package com.lonard.camerlangproject.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityConsultationBinding
import com.lonard.camerlangproject.databinding.ActivitySettingsUserBinding
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModel
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModelFactory
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref
import com.lonard.camerlangproject.db.user_datastore.UserModel
import com.lonard.camerlangproject.ui.dataStore

class SettingsUserActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySettingsUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySettingsUserBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val localUserPref = LocalUser_pref.getLocalUserInstance(dataStore)

        val localUserViewModel = ViewModelProvider(
            this,
            LocalUserViewModelFactory(localUserPref)
        )[LocalUserViewModel::class.java]

        localUserViewModel.getLocalUser().observe(this) { localUser ->
            bind.apply {
                backBtn.setOnClickListener {
                    finish()
                }

                settingsUserSection1Column.setText(localUser.name)
                settingsUserSection2Column.setText(localUser.age.toString())
                settingsUserSection3Column.setText(localUser.profession)
                settingsUserSection4Column.setText(localUser.status)

                val savedName = settingsUserSection1Column.text.toString()
                val savedAge = settingsUserSection2Column.text.toString()
                val savedProfession = settingsUserSection3Column.text.toString()
                val savedStatus = settingsUserSection4Column.text.toString()

                localUserViewModel.modifyLocalUser(
                    UserModel(
                        savedName,
                        savedAge,
                        savedProfession,
                        savedStatus
                    )
                )
            }
        }
    }
}