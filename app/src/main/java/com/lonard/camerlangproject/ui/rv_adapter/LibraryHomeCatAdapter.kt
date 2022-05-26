package com.lonard.camerlangproject.ui.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ConsultationHistoryRvBoxBinding
import com.lonard.camerlangproject.databinding.OverflowRvBoxCategoryBinding
import com.squareup.picasso.Picasso

class LibraryHomeCatAdapter(private val categoryList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryHomeCatAdapter.ViewHolder>() {
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
            Picasso.get().load(iconUrl).into(categoryDataListImage)

            categoryDataListText.text = catDesc
        }
    }

    override fun getItemCount(): Int = categoryList.size

    class ViewHolder(var bind: OverflowRvBoxCategoryBinding): RecyclerView.ViewHolder(bind.root)
}