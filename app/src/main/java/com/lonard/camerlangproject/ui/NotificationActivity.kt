package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.api.NotificationResponseItem
import com.lonard.camerlangproject.databinding.ActivityLibraryHomeBinding
import com.lonard.camerlangproject.databinding.ActivityNotificationBinding
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.NotificationCatAdapter
import com.lonard.camerlangproject.ui.rv_adapter.NotificationListAdapter

class NotificationActivity : AppCompatActivity() {
    private lateinit var bind: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    private fun showCategories(categoryItems: List<NotificationCategoryItem>) {
        bind.notificationCatListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val categoryAdapter = NotificationCatAdapter(categoryItems as ArrayList<NotificationResponseItem>)
        bind.notificationCatListRv.adapter = categoryAdapter
    }

    private fun showNotificationList(notificationItems: List<NotificationItem>) {
        bind.notificationDataListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val notificationAdapter = NotificationListAdapter(notificationItems as ArrayList<NotificationResponseItem>)
        bind.notificationDataListRv.adapter = notificationAdapter
    }
}