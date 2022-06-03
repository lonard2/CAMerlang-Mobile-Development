package com.lonard.camerlangproject.ui.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDetailImgEntity
import com.lonard.camerlangproject.ui.homepage.ArticleDetailActivity
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailProductAdapter
import com.squareup.picasso.Picasso

class LibraryDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLibraryDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val diseaseParcel = intent.getParcelableExtra<LibraryContentEntity>(EXTRA_DISEASE) as LibraryContentEntity

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(diseaseParcel.selectedItemImgUrl).into(libDetailHeaderPic)

            diseaseTypeInfo.text = diseaseParcel.selectedLibItemType
            diseaseLevelInfo.text = diseaseParcel.selectedLibItemLevel
            diseaseName.text = diseaseParcel.selectedLibItemName

            libItemShortDesc.text = diseaseParcel.selectedLibItemShortDesc

            libDetailHeaderPic.setOnClickListener {
                val showImageIntent = Intent(this@LibraryDetailActivity, LibraryDetailImageShowActivity::class.java)
                showImageIntent.putExtra(LibraryDetailImageShowActivity.EXTRA_PIC, diseaseParcel.selectedItemImgUrl)

                startActivity(showImageIntent)
            }

        }
    }

    private fun showMoreImagesContent(imagesItems: List<ImageItem>) {
        bind.moreImagesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val moreImagesAdapter = LibraryDetailMoreAdapter(imageItem as ArrayList<LibraryResponseItem>)
        bind.moreImagesSectionRv.adapter = moreImagesAdapter

        moreImagesAdapter.setOnItemClickCallback(object: LibraryDetailImgAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ImageItem) {
                seeZoomedImg(data)
            }
        })
    }

    private fun seeZoomedImg(images: LibraryDetailImgEntity) {
        val imagesList =
            images.apply {
                LibraryDetailImgEntity(
                    imageUrl,
                    imageDesc
                )
            }

        val zoomImgIntent = Intent(this@LibraryDetailActivity, ArticleDetailActivity::class.java)
        zoomImgIntent.putExtra(LibraryDetailImageShowActivity.EXTRA_IMAGE, imagesList)

        startActivity(zoomImgIntent)
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

    private fun seeZoomedImg(images: LibraryDetailImgEntity) {
        val images =
            images.apply {
                LibraryDetailImgEntity(
                    imageUrl,
                    imageDesc
                )
            }

        val zoomImgIntent = Intent(this@LibraryDetailActivity, ArticleDetailActivity::class.java)
        zoomImgIntent.putExtra(LibraryDetailImageShowActivity.EXTRA_IMAGE, images)

        startActivity(zoomImgIntent)
    }

    private fun showProductsContent(productItems: List<ProductItem>) {
        bind.productsSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val productsListAdapter = LibraryDetailProductAdapter(productItems as ArrayList<LibraryResponseItem>)
        bind.productsSectionRv.adapter = productsListAdapter
    }

    companion object {
        const val EXTRA_DISEASE = "an_showed_skin_disease"
    }
}