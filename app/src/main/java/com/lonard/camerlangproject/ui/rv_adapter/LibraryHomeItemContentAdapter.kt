package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.RvInfoListingLibraryBinding
import com.squareup.picasso.Picasso

class LibraryHomeItemContentAdapter(private val alphabetSectionList: ArrayList<LibraryResponseItem>): RecyclerView.Adapter<LibraryHomeItemContentAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibraryHomeItemContentAdapter.ViewHolder {
        val bind = RvInfoListingLibraryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LibraryHomeItemContentAdapter.ViewHolder, position: Int) {
        val(alphabetIconUrl, alphabetSectionName, alphabetRvData) = alphabetSectionList[position]

        holder.bind.apply {
            Picasso.get().load(alphabetIconUrl).into(alphabetEmojiIcon)

            librarySectionIntro.text = alphabetSectionName

            libraryListingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            libraryListingRv.adapter = LibraryHomeItemListAdapter(alphabetRvData)
        }
    }

    override fun getItemCount(): Int = alphabetSectionList.size

    class ViewHolder(var bind: RvInfoListingLibraryBinding): RecyclerView.ViewHolder(bind.root)
}