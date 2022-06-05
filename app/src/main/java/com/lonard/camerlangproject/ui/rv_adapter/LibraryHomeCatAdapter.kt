package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxCategoryBinding
import com.lonard.camerlangproject.ui.ShimmerPlaceHolder
import com.squareup.picasso.Picasso

class LibraryHomeCatAdapter(private val categoryList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryHomeCatAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibraryHomeCatAdapter.ViewHolder {
        val bind = OverflowRvBoxCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LibraryHomeCatAdapter.ViewHolder, position: Int) {
        val(iconUrl, catDesc) = categoryList[position]

        holder.bind.apply {
            Picasso.get().load(iconUrl).placeholder(ShimmerPlaceHolder.active()).into(categoryDataListImage)

            categoryDataListText.text = catDesc
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(categoryList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = categoryList.size

    class ViewHolder(var bind: OverflowRvBoxCategoryBinding): RecyclerView.ViewHolder(bind.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoryItem)
    }
}