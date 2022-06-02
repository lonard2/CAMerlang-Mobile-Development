package com.lonard.camerlangproject.ui.rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.RvInfoListingLibraryBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.ArticleDetailActivity
import com.lonard.camerlangproject.ui.LibraryDetailActivity
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

            val listRv = LibraryHomeItemListAdapter(alphabetRvData)
            libraryListingRv.adapter = listRv

            listRv.setOnItemClickCallback(object: LibraryHomeItemListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemList) {
                    viewDetail(data)
                }
            })
        }
    }

    private fun viewArticle(disease: LibraryContentEntity) {
        val diseaseItem =
            disease.apply {
                LibraryContentEntity(
                    diseaseImg,
                    diseaseName,
                    diseaseCategory,
                    diseaseSeverity,
                    diseaseKeyword
                )
            }

        val diseaseDetailIntent = Intent(context, LibraryDetailActivity::class.java)
        diseaseDetailIntent.putExtra(LibraryDetailActivity.EXTRA_DISEASE, diseaseItem)

        ContextCompat.startActivity(context, diseaseDetailIntent, null)
    }

    override fun getItemCount(): Int = alphabetSectionList.size

    class ViewHolder(var bind: RvInfoListingLibraryBinding): RecyclerView.ViewHolder(bind.root)
}