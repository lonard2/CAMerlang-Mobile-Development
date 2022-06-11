package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity

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
        val(consultId, _, consultDateTime) = consultationList[position]

        holder.bind.apply {
            datetimeInfo.text = consultDateTime.toString()
            diagnosisId.text = holder.itemView.context.getString(R.string.consultation_id_format, consultId.toString())
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