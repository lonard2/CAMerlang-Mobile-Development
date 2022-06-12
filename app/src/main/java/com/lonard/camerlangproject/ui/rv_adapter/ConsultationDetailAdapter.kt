package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.RvBoxConsultationDetailResultBinding
import com.lonard.camerlangproject.db.consultation.DetectionResultEntity

class ConsultationDetailAdapter(private val problemList: ArrayList<DetectionResultEntity>): RecyclerView.Adapter<ConsultationDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = RvBoxConsultationDetailResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(detectionId, _, problem, problemPercentage) = problemList[position]

        holder.bind.apply {
            problemNumber.text = detectionId.plus(1).toString()
            problemShow.text = problem
            problemPercentageTxt.text = holder.itemView.context.getString(R.string.percentage_format, problemPercentage.toString())
        }
    }

    override fun getItemCount(): Int = problemList.size

    class ViewHolder(var bind: RvBoxConsultationDetailResultBinding): RecyclerView.ViewHolder(bind.root)
}