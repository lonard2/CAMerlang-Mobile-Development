package com.lonard.camerlangproject.ui.settings

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.FragmentSettingsMainBinding
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModel
import com.lonard.camerlangproject.db.user_datastore.LocalUserViewModelFactory
import com.lonard.camerlangproject.db.user_datastore.LocalUser_pref
import com.lonard.camerlangproject.ui.dataStore

class SettingsMainFragment : Fragment() {
    private var _bind: FragmentSettingsMainBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentSettingsMainBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val localPref = LocalUser_pref.getLocalUserInstance(requireActivity().dataStore)

        val localViewModel = ViewModelProvider(
            requireActivity(),
            LocalUserViewModelFactory(localPref)
        )[LocalUserViewModel::class.java]

        bind.apply {
            btnSettingsCard1.setOnClickListener {
                val userAccountIntent =
                    Intent(requireActivity(), SettingsUserActivity::class.java)
                startActivity(userAccountIntent,
                    ActivityOptions.makeSceneTransitionAnimation(requireActivity())
                        .toBundle()
                )
            }

            btnSettingsCard3.setOnClickListener {
                val localizationMenuIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(localizationMenuIntent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
            }

            btnSettingsCard4.setOnClickListener {
                val creditIntent = Intent(requireActivity(), CreditsActivity::class.java)
                startActivity(creditIntent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
            }
        }

        localViewModel.getStartUp().observe(viewLifecycleOwner) { appSetting ->

            bind.btnSettingsCard2.setOnClickListener {
                if (appSetting.darkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    bind.btnSettingsSelect2Text.text =
                        getString(R.string.dark_mode_ask)
                    bind.btnSettingsSelect2.setImageResource(R.drawable.night_icon)

                    localViewModel.setDarkTheme(false)

                    bind.btnSettingsSelect2.resources.getColor(
                        R.color.md_theme_light_onPrimary,
                        null
                    )

                    bind.btnSettingsSelect2.setBackgroundResource(R.color.settings_menu_2)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    bind.btnSettingsSelect2Text.text = getString(R.string.light_mode_ask)
                    bind.btnSettingsSelect2.setImageResource(R.drawable.light_mode_icon)

                    localViewModel.setDarkTheme(true)

                    bind.btnSettingsSelect2.resources.getColor(
                        R.color.md_theme_light_onPrimary,
                        null
                    )

                    bind.btnSettingsSelect2.setBackgroundResource(R.color.md_theme_light_tertiaryContainer)
                }
            }

            localViewModel.getLocalUser().observe(viewLifecycleOwner) { localUser ->
                bind.apply {
                    settingsMainName.text = localUser.name
                    settingsMainAge.text = getString(R.string.age_string_format, localUser.age)
                    settingsMainProfession.text = localUser.profession
                    settingsMainStatus.text = localUser.status
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}