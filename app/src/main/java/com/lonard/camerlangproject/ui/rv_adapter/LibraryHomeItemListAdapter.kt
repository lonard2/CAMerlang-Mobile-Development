package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxOutsideBinding
import com.squareup.picasso.Picasso

class LibraryHomeItemListAdapter(private val alphabetItemList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryHomeItemListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibraryHomeItemListAdapter.ViewHolder {
        val bind = OverflowRvBoxInsideBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LibraryHomeItemListAdapter.ViewHolder, position: Int) {
        val(itemPicUrl, itemName) = alphabetItemList[position]

        holder.bind.apply {
            Picasso.get().load(itemPicUrl).into(contentListImage)

            contentTitle.text = itemName
        }
    }

    override fun getItemCount(): Int = alphabetItemList.size

    class ViewHolder(var bind: OverflowRvBoxInsideBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: EntryItem)
    }
}