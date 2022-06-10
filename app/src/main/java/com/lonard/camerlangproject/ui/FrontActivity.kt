package com.lonard.camerlangproject.ui

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.camera.ScannerCameraActivity
import com.lonard.camerlangproject.databinding.ActivityFrontBinding
import com.lonard.camerlangproject.ui.consultation.ConsultationHistoryFragment
import com.lonard.camerlangproject.ui.homepage.HomepageFragment
import com.lonard.camerlangproject.ui.images.ImageTakenPreviewActivity.Companion.CAMERAX_RESPONSE_CODE
import com.lonard.camerlangproject.ui.library.LibraryHomeFragment
import com.lonard.camerlangproject.ui.settings.SettingsMainFragment
import java.util.*

class FrontActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFrontBinding
    private lateinit var selectedFragment: Fragment

    private val locale: String = Locale.getDefault().country

    private val animation = AnimatorSet()

    private var fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(bind.root)

        showAnimation()

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

        bind.scannerToActionFab.setOnClickListener{
            if(!checkAllCameraPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this,
                    CAMERA_PERMISSIONS,
                    CAMERAX_RESPONSE_CODE
                )
            }

            launchCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(!checkAllCameraPermissionsGranted()) {
                Snackbar.make(
                    bind.scannerToActionFab,
                    when (locale) {
                        "in" -> {
                            "Sayang sekali, kamu masih belum mengizinkan aplikasi ini untuk menggunakan fitur kamera. " +
                                    "Atur perizinan dulu ya di menu pengaturan.."
                        }
                        "en" -> {
                            "Ouch! You still didn't allow the application to use camera feature. " +
                                    "Please set your permission in the settings menu."
                        }
                        else -> "Camera permission error or didn't granted."
                    },
                    Snackbar.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    private fun launchCamera() {
        val cameraIntent = Intent(this, ScannerCameraActivity::class.java)

        startActivity(cameraIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishAffinity()
        finish()
    }

    private fun checkAllCameraPermissionsGranted() = CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showAnimation() {
        val navbar = ObjectAnimator.ofFloat(bind.bottomNavbar, View.ALPHA, 1f).setDuration(600)
        val fab = ObjectAnimator.ofFloat(bind.scannerToActionFab, View.ALPHA, 1f).setDuration(600)

        animation.apply {
            playTogether(navbar, fab)
        }
    }

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val PERMISSION_REQUEST_CODE = 10
    }

}