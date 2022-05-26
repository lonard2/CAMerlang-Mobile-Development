package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.squareup.picasso.Picasso

class LibraryDetailProductAdapter(private val skinProductList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryDetailProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibraryDetailProductAdapter.ViewHolder {
        val bind = OverflowRvBoxInsideDetailBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LibraryDetailProductAdapter.ViewHolder, position: Int) {
        val(productPicUrl, sellerPicUrl, productDesc) = skinProductList[position]

        holder.bind.apply {
            Picasso.get().load(productPicUrl).into(contentListImage)
            Picasso.get().load(sellerPicUrl).into(contentListImage)

            contentTitle.text = productDesc
        }
    }

    override fun getItemCount(): Int = skinProductList.size

    class ViewHolder(var bind: OverflowRvBoxInsideDetailBinding): RecyclerView.ViewHolder(bind.root)
}