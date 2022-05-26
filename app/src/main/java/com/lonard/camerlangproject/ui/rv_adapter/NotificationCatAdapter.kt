package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.NotificationResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxNotificationListBinding

class NotificationCatAdapter(private val notificationCatList: ArrayList<NotificationResponseItem>): RecyclerView.Adapter<NotificationCatAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(notificationCatList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = notificationCatList.size

    class ViewHolder(var bind: OverflowRvBoxNotificationListBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoryItem)
    }
}