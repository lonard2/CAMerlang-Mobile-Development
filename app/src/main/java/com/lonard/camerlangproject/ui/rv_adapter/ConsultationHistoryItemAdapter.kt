package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvOnlyPicBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.squareup.picasso.Picasso

class ConsultationHistoryItemAdapter(private val consultationList: ArrayList<ConsultationItemEntity>): RecyclerView.Adapter<ConsultationHistoryItemAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = ConsultationHistoryRvBoxBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(consultDatetime, consultId) = consultationList[position]

        holder.bind.apply {
            datetimeInfo.text = consultDatetime.toString()
            diagnosisId.text = consultId
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(consultationList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = consultationList.size

    class ViewHolder(var bind: ConsultationHistoryRvBoxBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ConsultationItemEntity)
    }
}