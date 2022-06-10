package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxNotificationListBinding
import com.lonard.camerlangproject.db.homepage.NotificationCatEntity

class NotificationCatAdapter(private val notificationCatList: ArrayList<NotificationCatEntity>): RecyclerView.Adapter<NotificationCatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvBoxNotificationListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, categoryName, _) = notificationCatList[position]

        holder.bind.apply {
            chipText.text = categoryName
        }
    }

    override fun getItemCount(): Int = notificationCatList.size

    class ViewHolder(var bind: OverflowRvBoxNotificationListBinding): RecyclerView.ViewHolder(bind.root)

}