package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideBinding
import com.lonard.camerlangproject.databinding.OverflowRvOnlyPicBinding
import com.squareup.picasso.Picasso

class LibraryDetailImgAdapter(private val otherPicList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryDetailImgAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibraryDetailImgAdapter.ViewHolder {
        val bind = OverflowRvOnlyPicBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LibraryDetailImgAdapter.ViewHolder, position: Int) {
        val(entryPicUrl) = otherPicList[position]

        holder.bind.apply {
            Picasso.get().load(entryPicUrl).into(contentListImage)
        }
    }

    override fun getItemCount(): Int = otherPicList.size

    class ViewHolder(var bind: OverflowRvOnlyPicBinding): RecyclerView.ViewHolder(bind.root)
}