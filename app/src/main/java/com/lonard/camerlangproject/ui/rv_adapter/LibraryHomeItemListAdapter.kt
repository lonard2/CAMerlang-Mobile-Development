package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideSmallBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
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
            Picasso.get().load(entryThumbnail).into(contentListImage)

            contentTitle.text = entryName

        }
    }

    override fun getItemCount(): Int = alphabetItemList.size

    class ViewHolder(var bind: OverflowRvBoxInsideSmallBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryContentEntity)
    }
}