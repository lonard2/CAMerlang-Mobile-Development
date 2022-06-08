package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.databinding.RvExpertListsHomepageBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class HomepageExpertContentListAdapter(private val itemList: ArrayList<ExpertEntity>): RecyclerView.Adapter<HomepageExpertContentListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = RvExpertListsHomepageBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, _, itemName, itemThumbnail, itemSellerPic, _, _) = peopleList[position]

        holder.bind.apply {
            Picasso.get().load(expertDataPic).placeholder(ShimmerPlaceHolder.active()).into(expertImage)

            expertName.text = expertDataName
            expertSpecialization.text = expertDataSpecialization
            ratingValue.text = expertDataRating
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(var bind: RvExpertListsHomepageBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ExpertEntity)
    }
}