package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.squareup.picasso.Picasso

class HomepageArticleContentListAdapter(private val itemList: ArrayList<ArticleEntity>): RecyclerView.Adapter<HomepageArticleContentListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomepageArticleContentListAdapter.ViewHolder {
        val bind = OverflowRvBoxOutsideBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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