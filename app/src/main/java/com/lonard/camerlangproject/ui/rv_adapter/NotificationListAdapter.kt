package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.NotificationRvBoxBinding
import com.lonard.camerlangproject.db.homepage.NotificationContentEntity

class NotificationListAdapter(private val notificationList: ArrayList<NotificationContentEntity>): RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = NotificationRvBoxBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, infoContent, infoDetail, type, datetime, _) = notificationList[position]

        holder.bind.apply {
            notificationType.text = type
            datetimeInfo.text = datetime
            notificationContent.text = infoContent
            notificationDetail.text = infoDetail
        }

    }

    override fun getItemCount(): Int = notificationList.size

    class ViewHolder(var bind: NotificationRvBoxBinding): RecyclerView.ViewHolder(bind.root)
}