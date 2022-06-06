package com.lonard.camerlangproject.ui.rv_adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvOnlyPicBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class LibraryDetailImgAdapter(private val otherPicList: ArrayList<LibraryContentEntity>): RecyclerView.Adapter<LibraryDetailImgAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvOnlyPicBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(entryPicUrl) = otherPicList[position]

        holder.bind.apply {
            if (entryPicUrl != null) {
                Picasso.get().load(entryPicUrl).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)
            }
        }

        holder.itemView.setOnClickListener {
            val sharedAnim: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.bind.contentListImage, "zoomed_image"),
                )

            onItemClickCallback.onItemClicked(otherPicList[holder.bindingAdapterPosition], sharedAnim.toBundle())
        }
    }

    override fun getItemCount(): Int = otherPicList.size

    class ViewHolder(var bind: OverflowRvOnlyPicBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?)
    }
}