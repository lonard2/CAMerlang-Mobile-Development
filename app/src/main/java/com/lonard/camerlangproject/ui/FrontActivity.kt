package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityFrontBinding
import com.lonard.camerlangproject.ui.settings.SettingsMainFragment

class FrontActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFrontBinding
    private lateinit var selectedFragment: Fragment

    var fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.bottomNavbar.setOnItemSelectedListener { navbarItem ->
            when(navbarItem.itemId) {
                R.id.navbar_menu_1 -> selectedFragment = HomepageFragment()
                R.id.navbar_menu_2 -> selectedFragment = ConsultationHistoryFragment()
                R.id.navbar_menu_3 -> selectedFragment = LibraryHomeFragment()
                R.id.navbar_menu_4 -> selectedFragment = SettingsMainFragment()
            }

            fragmentManager.commit {
                replace(R.id.fragment_container, selectedFragment)
            }

            true
        }

        bind.bottomNavbar.selectedItemId = R.id.navbar_menu_1
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishAffinity()
        finish()
    }


}