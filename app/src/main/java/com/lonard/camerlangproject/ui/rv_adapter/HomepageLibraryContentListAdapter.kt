package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideSmallBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.squareup.picasso.Picasso

class HomepageLibraryContentListAdapter(private val itemList: ArrayList<LibraryContentEntity>): RecyclerView.Adapter<HomepageLibraryContentListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvBoxInsideSmallBinding.inflate(LayoutInflater.from(parent.context))
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

    class ViewHolder(var bind: OverflowRvBoxInsideSmallBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryContentEntity)
    }
}