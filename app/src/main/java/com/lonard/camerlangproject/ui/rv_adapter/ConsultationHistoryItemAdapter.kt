package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvOnlyPicBinding
import com.squareup.picasso.Picasso

class ConsultationHistoryItemAdapter(private val consultationList: ArrayList<ConsultationResponseItem>): RecyclerView.Adapter<ConsultationHistoryItemAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConsultationHistoryItemAdapter.ViewHolder {
        val bind = ConsultationHistoryRvBoxBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ConsultationHistoryItemAdapter.ViewHolder, position: Int) {
        val(datetime, outcome, id) = consultationList[position]

        holder.bind.apply {
            datetimeInfo.text = datetime
            diagnosisOutcomeInfo.text = outcome
            diagnosisId.text = id
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(consultationList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = consultationList.size

    class ViewHolder(var bind: ConsultationHistoryRvBoxBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: HistoryItem)
    }
}