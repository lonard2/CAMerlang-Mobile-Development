package com.lonard.camerlangproject.ui.rv_adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideSmallBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class LibraryHomeItemListAdapter(private val alphabetItemList: ArrayList<LibraryContentEntity>): RecyclerView.Adapter<LibraryHomeItemListAdapter.ViewHolder>() {

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
        val(_, _, entryName, entryThumbnail, _, _, _, _) = alphabetItemList[position]

        holder.bind.apply {
            Picasso.get().load(entryThumbnail).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)

            contentTitle.text = entryName
        }

        holder.itemView.setOnClickListener {
            val sharedAnim = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.itemView.context as Activity,
                Pair(holder.bind.contentListImage, "disease_pic"),
                Pair(holder.bind.contentTitle, "disease_name"),
            )

            onItemClickCallback.onItemClicked(alphabetItemList[holder.bindingAdapterPosition], sharedAnim.toBundle())
        }
    }

    override fun getItemCount(): Int = alphabetItemList.size

    class ViewHolder(var bind: OverflowRvBoxInsideSmallBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?)
    }
}