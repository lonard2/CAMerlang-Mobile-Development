package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.databinding.RvInfoListingHomepageBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.ArticleResponse
import com.lonard.camerlangproject.db.homepage.DataItem
import com.squareup.picasso.Picasso

class HomepageContentListAdapter(private val itemList: ArrayList<ArticleEntity>): RecyclerView.Adapter<HomepageContentListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomepageContentListAdapter.ViewHolder {
        val bind = OverflowRvBoxOutsideBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: HomepageContentListAdapter.ViewHolder, position: Int) {
        val(_, _, itemName, itemThumbnail, itemType, _, _) = itemList[position]

        holder.bind.apply {
            Picasso.get().load(itemThumbnail).into(contentListImage)

            contentTitle.text = itemName
            contentCategory.text = itemType
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(itemList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(var bind: OverflowRvBoxOutsideBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticleEntity)
    }
}