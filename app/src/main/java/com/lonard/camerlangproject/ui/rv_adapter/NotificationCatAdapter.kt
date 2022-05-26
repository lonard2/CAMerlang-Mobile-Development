package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.NotificationResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxNotificationListBinding

class NotificationCatAdapter(private val notificationCatList: ArrayList<NotificationResponseItem>): RecyclerView.Adapter<NotificationCatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationCatAdapter.ViewHolder {
        val bind = OverflowRvBoxNotificationListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: NotificationCatAdapter.ViewHolder, position: Int) {
        val(categoryId, categoryName) = notificationCatList[position]

        holder.bind.apply {
            chipText.text = categoryName
        }
    }

    override fun getItemCount(): Int = notificationCatList.size

    class ViewHolder(var bind: OverflowRvBoxNotificationListBinding): RecyclerView.ViewHolder(bind.root)
}