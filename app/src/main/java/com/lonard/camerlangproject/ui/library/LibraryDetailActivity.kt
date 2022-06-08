package com.lonard.camerlangproject.ui.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDetailImgEntity
import com.lonard.camerlangproject.formatDateTime
import com.lonard.camerlangproject.mvvm.HomepageViewModel
import com.lonard.camerlangproject.mvvm.HomepageViewModelFactory
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.mvvm.LibraryViewModelFactory
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.lonard.camerlangproject.ui.homepage.ArticleDetailActivity
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailImgAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailProductAdapter
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class LibraryDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailBinding
    private lateinit var context: Context

    private val locale: String = Locale.getDefault().language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLibraryDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val diseaseParcel = intent.getParcelableExtra<LibraryContentEntity>(EXTRA_DISEASE) as LibraryContentEntity

        val libraryFactory: LibraryViewModelFactory = LibraryViewModelFactory.getFactory(this)
        val libraryViewModel: LibraryViewModel by viewModels {
            libraryFactory
        }

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(diseaseParcel.thumbnailPic).into(libDetailHeaderPic)

            diseaseTypeInfo.text = diseaseParcel.bodyType
            diseaseLevelInfo.text = diseaseParcel.problemSeverity
            diseaseName.text = diseaseParcel.name

            Picasso.get().load(diseaseParcel.expertImage).into(expertPic)

            expertName.text = diseaseParcel.expertName
            expertSpecialization.text = diseaseParcel.expertSpecialization
            expertVerifiedDate.text = getString(R.string.expert_verifed_date_format, diseaseParcel.verifiedAt?.formatDateTime())

            libItemShortDesc.text = diseaseParcel.contentHeader
            libItemContent.text = diseaseParcel.content

            libDetailHeaderPic.setOnClickListener {
                val showImageIntent = Intent(this@LibraryDetailActivity, ImageShowActivity::class.java)
                showImageIntent.putExtra(ImageShowActivity.EXTRA_PIC, diseaseParcel.thumbnailPic)

                val sharedAnim =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(bind.libDetailHeaderPic, "zoomed_image"),
                    )

                startActivity(showImageIntent, sharedAnim.toBundle())
            }

            libraryViewModel.retrieveImagesData().observe() { imagesList ->
                libraryViewModel.retrieveLibraryEntriesList().observe(this@LibraryDetailActivity) { entriesList ->
                    libraryViewModel.retrieveProductsInfo().observe(this@LibraryDetailActivity) { productList ->
                        if(productList != null) {
                            when (productList) {
                                is DataLoadResult.Loading -> {
                                    loadFrame.visibility = View.VISIBLE
                                    loadAnimLottie.visibility = View.VISIBLE
                                }

                                is DataLoadResult.Successful -> {
                                    loadFrame.visibility = View.GONE
                                    loadAnimLottie.visibility = View.GONE

                                    val products = productList.data

                                    showProductsContent(products)
                                }

                                is DataLoadResult.Failed -> {
                                    loadFrame.visibility = View.GONE
                                    loadAnimLottie.visibility = View.GONE

                                    Snackbar.make(
                                        productsSectionRv, when (locale) {
                                            "in" -> {
                                                "Data produk terkait item masalah ini tidak bisa ditampilkan. Silakan coba lagi ya."
                                            }
                                            "en" -> {
                                                "Ouch, the products data regarding this problem item cannot be shown to you. Please try again."
                                            }
                                            else -> {
                                                "Error in products retrieval for a specific item."
                                            }
                                        }, Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        if (entriesList != null) {
                            when (entriesList) {
                                is DataLoadResult.Loading -> {
                                    loadFrame.visibility = View.VISIBLE
                                    loadAnimLottie.visibility = View.VISIBLE
                                }

                                is DataLoadResult.Successful -> {
                                    loadFrame.visibility = View.GONE
                                    loadAnimLottie.visibility = View.GONE

                                    val libraryEntries = entriesList.data

                                    showOtherEntriesContent(libraryEntries)
                                }

                                is DataLoadResult.Failed -> {
                                    loadFrame.visibility = View.GONE
                                    loadAnimLottie.visibility = View.GONE

                                    Snackbar.make(
                                        productsSectionRv, when (locale) {
                                            "in" -> {
                                                "Data entri pustaka terkait item masalah ini tidak bisa ditampilkan. Silakan coba lagi ya."
                                            }
                                            "en" -> {
                                                "Ouch, the library entries data regarding this problem item cannot be shown to you. Please try again."
                                            }
                                            else -> {
                                                "Error in library entries retrieval for a specific item."
                                            }
                                        }, Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
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

    private fun showProductsContent(productItems: List<ProductEntity>) {
        bind.productsSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val productsListAdapter = LibraryDetailProductAdapter(productItems as ArrayList<ProductEntity>)
        bind.productsSectionRv.adapter = productsListAdapter
    }

    companion object {
        const val EXTRA_DISEASE = "an_showed_skin_disease"
    }
}