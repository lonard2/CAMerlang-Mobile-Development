package com.lonard.camerlangproject.ui.rv_adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class HomepageArticleContentListAdapter(private val itemList: ArrayList<ArticleEntity>): RecyclerView.Adapter<HomepageArticleContentListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvBoxOutsideBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, _, itemName, itemThumbnail, itemType, _, _) = itemList[position]

        holder.bind.apply {
            Picasso.get().load(itemThumbnail).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)

            contentTitle.text = itemName
            contentCategory.text = itemType
        }

        holder.itemView.setOnClickListener {
            val sharedAnim: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.bind.contentListImage, "article_pic"),
                    Pair(holder.bind.contentTitle, "article_name"),
                )

            onItemClickCallback.onItemClicked(itemList[holder.bindingAdapterPosition], sharedAnim.toBundle())
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(var bind: OverflowRvBoxOutsideBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticleEntity, animBundle: Bundle?)
    }
}