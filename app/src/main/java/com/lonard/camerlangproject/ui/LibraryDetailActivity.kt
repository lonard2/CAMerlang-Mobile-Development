package com.lonard.camerlangproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ActivityConsultationHistoryBinding
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailBinding
import com.lonard.camerlangproject.ui.rv_adapter.ConsultationHistoryItemAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailProductAdapter
import com.squareup.picasso.Picasso

class LibraryDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLibraryDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(selectedItemImgUrl).into(libDetailHeaderPic)

            diseaseTypeInfo.text = selectedLibItemType
            diseaseLevelInfo.text = selectedLibItemLevel
            diseaseName.text = selectedLibItemName

            libItemShortDesc.text = selectedLibItemShortDesc

            libDetailHeaderPic.setOnClickListener {
                finish()
            }

            val showImageIntent = Intent(this@LibraryDetailActivity, LibraryDetailImageShowActivity::class.java)
            showImageIntent.putExtra(LibraryDetailImageShowActivity.EXTRA_PIC, selectedItemImgUrl)

            startActivity(showImageIntent)
        }
    }

    private fun showMoreImagesContent(imagesItems: List<ImageItem>) {
        bind.moreImagesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val moreImagesAdapter = LibraryDetailMoreAdapter(imageItem as ArrayList<LibraryResponseItem>)
        bind.moreImagesSectionRv.adapter = moreImagesAdapter

        moreImagesAdapter.setOnItemClickCallback(object: ConsultationHistoryItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ImageItem) {

            }
        })
    }

    private fun showOtherEntriesContent(entryItems: List<EntryItem>) {
        bind.otherEntriesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val entriesListAdapter = LibraryDetailMoreAdapter(entryItems as ArrayList<LibraryResponseItem>)
        bind.otherEntriesSectionRv.adapter = entriesListAdapter

        entriesListAdapter.setOnItemClickCallback(object: LibraryDetailMoreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: EntryItem) {

            }
        })
    }

    private fun showProductsContent(productItems: List<ProductItem>) {
        bind.productsSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val productsListAdapter = LibraryDetailProductAdapter(productItems as ArrayList<LibraryResponseItem>)
        bind.productsSectionRv.adapter = productsListAdapter

        productsListAdapter.setOnItemClickCallback(object: LibraryDetailProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductItem) {

            }
        })
    }

    companion object {
        const val EXTRA_DISEASE = "an_showed_skin_disease"
    }
}