package com.lonard.camerlangproject.ui.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDetailImgEntity
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.lonard.camerlangproject.ui.homepage.ArticleDetailActivity
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailImgAdapter
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
                val showImageIntent = Intent(this@LibraryDetailActivity, ImageShowActivity::class.java)
                showImageIntent.putExtra(ImageShowActivity.EXTRA_PIC, diseaseParcel.selectedItemImgUrl)

                startActivity(showImageIntent)
            }

        }
    }

    private fun showMoreImagesContent(imagesItems: List<LibraryContentEntity>) {
        bind.moreImagesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val moreImagesAdapter = LibraryDetailImgAdapter(imagesItems as ArrayList<LibraryContentEntity>)
        bind.moreImagesSectionRv.adapter = moreImagesAdapter

        moreImagesAdapter.setOnItemClickCallback(object: LibraryDetailImgAdapter.OnItemClickCallback {
            override fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?) {
                if (animBundle != null) {
                    seeZoomedImg(data, animBundle)
                }
            }
        })
    }

    private fun seeZoomedImg(images: LibraryContentEntity, bundle: Bundle) {
        val imagesList =
            images.apply {
                LibraryDetailImgEntity(
                    imageUrl,
                    imageDesc
                )
            }

        val zoomImgIntent = Intent(this@LibraryDetailActivity, ImageShowActivity::class.java)
        zoomImgIntent.putExtra(ImageShowActivity.EXTRA_PIC, imagesList)

        startActivity(zoomImgIntent, bundle)
    }

    private fun showOtherEntriesContent(entryItems: List<LibraryContentEntity>) {
        bind.otherEntriesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val entriesListAdapter = LibraryDetailMoreAdapter(entryItems as ArrayList<LibraryContentEntity>)
        bind.otherEntriesSectionRv.adapter = entriesListAdapter

        entriesListAdapter.setOnItemClickCallback(object: LibraryDetailMoreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?) {
                if (animBundle != null) {
                    viewOtherEntry(data, animBundle)
                }
            }
        })
    }

    private fun viewOtherEntry(entryModel: LibraryContentEntity, bundle: Bundle) {
        val libraryEntries =
            entryModel.apply {
                LibraryContentEntity(
                    id,
                    createdAt,
                    name,
                    thumbnailPic,
                    bodyType,
                    problemSeverity,
                    contentHeader,
                    content
                )
            }

        val switchToOtherEntryIntent = Intent(this@LibraryDetailActivity, LibraryDetailActivity::class.java)
        switchToOtherEntryIntent.putExtra(EXTRA_DISEASE, libraryEntries)

        startActivity(switchToOtherEntryIntent, bundle)
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