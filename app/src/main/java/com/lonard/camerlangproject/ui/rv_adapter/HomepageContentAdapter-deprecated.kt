package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.RvInfoListingHomepageBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.ui.homepage.ArticleDetailActivity

class `HomepageContentAdapter-deprecated`(private val infoSectionList: ArrayList<HomepageResponseItem>): RecyclerView.Adapter<`HomepageContentAdapter-deprecated`.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): `HomepageContentAdapter-deprecated`.ViewHolder {
        val bind = RvInfoListingHomepageBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: `HomepageContentAdapter-deprecated`.ViewHolder, position: Int) {
        val(sectionName, sectionDetail, rvData) = infoSectionList[position]

        holder.bind.apply {
            sectionHead.text = sectionName
            sectionSubhead.text = sectionDetail

            articleOverflowRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            articleOverflowRecyclerview.setHasFixedSize(true)

            val overflowRvAdapter = HomepageArticleContentListAdapter(rvData)
            articleOverflowRecyclerview.adapter = overflowRvAdapter
        }
    }

    override fun getItemCount(): Int = infoSectionList.size

    class ViewHolder(var bind: RvInfoListingHomepageBinding): RecyclerView.ViewHolder(bind.root)
}