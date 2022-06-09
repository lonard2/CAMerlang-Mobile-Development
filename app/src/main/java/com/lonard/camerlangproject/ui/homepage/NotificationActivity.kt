package com.lonard.camerlangproject.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.api.NotificationResponseItem
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.homepage.NotificationCatEntity
import com.lonard.camerlangproject.db.homepage.NotificationContentEntity
import com.lonard.camerlangproject.mvvm.HomepageViewModel
import com.lonard.camerlangproject.mvvm.HomepageViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.NotificationCatAdapter
import com.lonard.camerlangproject.ui.rv_adapter.NotificationListAdapter
import java.util.*
import kotlin.collections.ArrayList

class NotificationActivity : AppCompatActivity() {
    private lateinit var bind: ActivityNotificationBinding

    private val locale: String = Locale.getDefault().language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val homeFactory: HomepageViewModelFactory = HomepageViewModelFactory.getFactory(this)
        val homeViewModel: HomepageViewModel by viewModels {
            homeFactory
        }

        bind.apply {
            backBtn.setOnClickListener {
                finishAfterTransition()
            }

            homeViewModel.getNotificationCategories().observe(this@NotificationActivity) { notificationCatList ->
                homeViewModel.getNotificationContent().observe(this@NotificationActivity) { notificationList ->
                    if (notificationList != null) {
                        when (notificationList) {
                            is DataLoadResult.Loading -> {
                                loadFrame.visibility = View.VISIBLE
                                loadAnimLottie.visibility = View.VISIBLE
                            }

                            is DataLoadResult.Successful -> {
                                loadFrame.visibility = View.GONE
                                loadAnimLottie.visibility = View.GONE

                                val notifications = notificationList.data

                                showNotificationList(notifications)
                            }

                            is DataLoadResult.Failed -> {
                                loadFrame.visibility = View.GONE
                                loadAnimLottie.visibility = View.GONE

                                Snackbar.make(
                                    notificationDataListRv, when (locale) {
                                        "in" -> {
                                            "Aduh, data notifikasi tidak dapat ditampilkan. Silakan coba lagi ya."
                                        }
                                        "en" -> {
                                            "Ouch, the notification data cannot be shown to you. Please try again."
                                        }
                                        else -> {
                                            "Error in notification data retrieval."
                                        }
                                    }, Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    if (notificationCatList != null) {
                        when (notificationCatList) {
                            is DataLoadResult.Loading -> {
                                loadFrame.visibility = View.VISIBLE
                                loadAnimLottie.visibility = View.VISIBLE
                            }

                            is DataLoadResult.Successful -> {
                                loadFrame.visibility = View.GONE
                                loadAnimLottie.visibility = View.GONE

                                val notificationCat = notificationCatList.data

                                showCategories(notificationCat)
                            }

                            is DataLoadResult.Failed -> {
                                loadFrame.visibility = View.GONE
                                loadAnimLottie.visibility = View.GONE

                                Snackbar.make(
                                    notificationCatListRv, when (locale) {
                                        "in" -> {
                                            "Aduh, data kategori notifikasi tidak dapat ditampilkan. " +
                                                    "Silakan coba lagi ya."
                                        }
                                        "en" -> {
                                            "Ouch, the notification categories data cannot be shown to you. " +
                                                    "Please try again."
                                        }
                                        else -> {
                                            "Error in notification categories data retrieval."
                                        }
                                    }, Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showCategories(categoryItems: List<NotificationCatEntity>) {
        bind.notificationCatListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val categoryAdapter = NotificationCatAdapter(categoryItems as ArrayList<NotificationCatEntity>)
        bind.notificationCatListRv.adapter = categoryAdapter
    }

    private fun showNotificationList(notificationItems: List<NotificationContentEntity>) {
        bind.notificationDataListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val notificationAdapter = NotificationListAdapter(notificationItems as ArrayList<NotificationContentEntity>)
        bind.notificationDataListRv.adapter = notificationAdapter
    }
}