package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.NotificationResponseItem
import com.lonard.camerlangproject.databinding.NotificationRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxNotificationListBinding

class NotificationListAdapter(private val notificationList: ArrayList<NotificationResponseItem>): RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: NotificationListAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: NotificationListAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationListAdapter.ViewHolder {
        val bind = NotificationRvBoxBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: NotificationListAdapter.ViewHolder, position: Int) {
        val(type, datetime, infoContent, infoDetail) = notificationList[position]

        holder.bind.apply {
            notificationType.text = type
            datetimeInfo.text = datetime
            notificationContent.text = infoContent
            notificationDetail.text = infoDetail
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(notificationList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = notificationList.size

    class ViewHolder(var bind: NotificationRvBoxBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: NotificationItem)
    }
}