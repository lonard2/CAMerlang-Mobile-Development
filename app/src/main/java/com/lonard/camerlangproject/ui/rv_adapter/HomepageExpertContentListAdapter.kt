package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.databinding.RvExpertListsHomepageBinding
import com.lonard.camerlangproject.db.consultation.ExpertEntity
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class HomepageExpertContentListAdapter(private val peopleList: ArrayList<ExpertEntity>): RecyclerView.Adapter<HomepageExpertContentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = RvExpertListsHomepageBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, expertShownName, expertPicUrl, expertRole, expertScore, _) = peopleList[position]

        holder.bind.apply {
            Picasso.get().load(expertPicUrl).placeholder(ShimmerPlaceHolder.active()).into(expertImage)

            expertName.text = expertShownName
            expertSpecialization.text = expertRole
            ratingValue.text = expertScore.toString()
        }
    }

    override fun getItemCount(): Int = peopleList.size

    class ViewHolder(var bind: RvExpertListsHomepageBinding): RecyclerView.ViewHolder(bind.root)
}