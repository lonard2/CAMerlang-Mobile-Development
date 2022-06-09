package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvOnlyPicBinding
import com.lonard.camerlangproject.databinding.RvBoxConsultationDetailResultBinding
import com.lonard.camerlangproject.db.consultation.ConsultationDetectionItemEntity
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.squareup.picasso.Picasso

class ConsultationDetailAdapter(private val consultationList: ArrayList<ConsultationDetectionItemEntity>): RecyclerView.Adapter<ConsultationDetailAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = RvBoxConsultationDetailResultBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(outcomeId, outcomeName, outcomePercent) = consultationList[position]

        holder.bind.apply {
            problemNumber.text = outcomeId.toString()
            problemShow.text = outcomeName
            problemPercentageTxt.text = outcomePercent
        }
    }

    override fun getItemCount(): Int = consultationList.size

    class ViewHolder(var bind: RvBoxConsultationDetailResultBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ConsultationItemEntity)
    }
}