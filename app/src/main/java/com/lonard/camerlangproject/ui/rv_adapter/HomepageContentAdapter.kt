package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.RvInfoListingHomepageBinding

class HomepageContentAdapter(private val infoSectionList: ArrayList<HomepageResponseItem>): RecyclerView.Adapter<HomepageContentAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomepageContentAdapter.ViewHolder {
        val bind = RvInfoListingHomepageBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: HomepageContentAdapter.ViewHolder, position: Int) {
        val(sectionName, sectionDetail, rvData) = infoSectionList[position]

        holder.bind.apply {
            sectionHead.text = sectionName
            sectionSubhead.text = sectionDetail

            articleOverflowRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            articleOverflowRecyclerview.setHasFixedSize(true)
            articleOverflowRecyclerview.adapter = HomepageContentListAdapter(rvData)
        }
    }

    override fun getItemCount(): Int = infoSectionList.size

    class ViewHolder(var bind: RvInfoListingHomepageBinding): RecyclerView.ViewHolder(bind.root)
}