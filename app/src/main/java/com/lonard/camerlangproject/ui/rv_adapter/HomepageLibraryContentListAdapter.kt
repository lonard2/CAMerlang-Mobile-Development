package com.lonard.camerlangproject.ui.rv_adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideSmallBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
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
        val(_, itemName, itemThumbnail, _, _, _, _) = itemList[position]

        holder.bind.apply {
            Picasso.get().load(itemThumbnail).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)

            contentTitle.text = itemName
        }

        holder.itemView.setOnClickListener {
            val sharedAnim: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.bind.contentListImage, "disease_pic"),
                    Pair(holder.bind.contentTitle, "disease_name"),
                )

            onItemClickCallback.onItemClicked(itemList[holder.bindingAdapterPosition], sharedAnim.toBundle())
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(var bind: OverflowRvBoxInsideSmallBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?)
    }
}