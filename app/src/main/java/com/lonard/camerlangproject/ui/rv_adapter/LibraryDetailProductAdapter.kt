package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.databinding.OverflowRvBoxInsideDetailBinding
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class LibraryDetailProductAdapter(private val skinProductList: ArrayList<ProductEntity>): RecyclerView.Adapter<LibraryDetailProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = OverflowRvBoxInsideDetailBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(_, productName, productPicUrl, _, sellerPicUrl) = skinProductList[position]

        holder.bind.apply {
            Picasso.get().load(productPicUrl).placeholder(ShimmerPlaceHolder.active()).into(contentListImage)
            Picasso.get().load(sellerPicUrl).placeholder(ShimmerPlaceHolder.active()).into(contentListImageSellerPic)

            contentTitle.text = productName
        }
    }

    override fun getItemCount(): Int = skinProductList.size

    class ViewHolder(var bind: OverflowRvBoxInsideDetailBinding): RecyclerView.ViewHolder(bind.root)
}