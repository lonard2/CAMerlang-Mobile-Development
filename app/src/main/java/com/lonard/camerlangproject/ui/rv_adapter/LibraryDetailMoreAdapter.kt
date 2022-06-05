package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDataItem
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class LibraryDetailMoreAdapter(private val diseaseEntriesList: ArrayList<LibraryContentEntity>): RecyclerView.Adapter<LibraryDetailMoreAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvBoxInsideBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(entryPicUrl, entryDesc) = diseaseEntriesList[position]

        holder.bind.apply {
            Picasso.get().load(entryPicUrl).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)

            contentTitle.text = entryDesc
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(diseaseEntriesList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = diseaseEntriesList.size

    class ViewHolder(var bind: OverflowRvBoxInsideBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: EntryItem)
    }
}