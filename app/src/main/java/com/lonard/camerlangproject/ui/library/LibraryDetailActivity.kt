package com.lonard.camerlangproject.ui.library

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityLibraryDetailBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.ProblemImagesEntity
import com.lonard.camerlangproject.formatDateTime
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.mvvm.LibraryViewModelFactory
import com.lonard.camerlangproject.ui.FrontActivity
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailImgAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailProductAdapter
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class LibraryDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLibraryDetailBinding

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
                val backIntent = Intent(this@LibraryDetailActivity, FrontActivity::class.java)

                backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(backIntent, ActivityOptions.makeSceneTransitionAnimation(this@LibraryDetailActivity).toBundle())
            }

            Picasso.get().load(diseaseParcel.thumbnailPic).into(libDetailHeaderPic)

            diseaseTypeInfo.text = diseaseParcel.bodyType
            diseaseLevelInfo.text = diseaseParcel.problemSeverity
            diseaseName.text = diseaseParcel.name

            Picasso.get().load(diseaseParcel.expertImage).into(expertPic)

            expertName.text = diseaseParcel.expertName
            expertSpecialization.text = diseaseParcel.expertSpecialization
            expertVerifiedDate.text = diseaseParcel.verifiedAt

            libItemShortDesc.text = diseaseParcel.contentHeader
            libItemContent.text = diseaseParcel.content


            libDetailHeaderPic.setOnClickListener {
                val showImageIntent = Intent(this@LibraryDetailActivity, ImageShowActivity::class.java)
                showImageIntent.putExtra(ImageShowActivity.EXTRA_PIC, diseaseParcel.thumbnailPic)
                showImageIntent.putExtra(ImageShowActivity.DIRECTED_FROM_INFORMATIVE_SECTIONS, true)

                val sharedAnim =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@LibraryDetailActivity,
                        Pair(bind.libDetailHeaderPic, "zoomed_image"),
                    )

                startActivity(showImageIntent, sharedAnim.toBundle())
            }

            libraryViewModel.retrieveProblemImages(diseaseParcel.name!!).observe(this@LibraryDetailActivity) { imageList ->
                if (imageList != null) {
                    when (imageList) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            val images = imageList.data

                            showMoreImagesContent(images)

                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE
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
            }

            libraryViewModel.retrieveProductsInfo().observe(this@LibraryDetailActivity) { productList ->
                if(productList != null) {
                    when (productList) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            val products = productList.data

                            showProductsContent(products)

                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE
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
            }

            libraryViewModel.retrieveLibraryEntriesList().observe(this@LibraryDetailActivity) { entriesList ->
                if (entriesList != null) {
                    when (entriesList) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            val libraryEntries = entriesList.data

                            showOtherEntriesContent(libraryEntries)

                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE
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

    override fun onBackPressed() {
        super.onBackPressed()

        val backIntent = Intent(this@LibraryDetailActivity, FrontActivity::class.java)

        backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(backIntent, ActivityOptions.
        makeSceneTransitionAnimation(this@LibraryDetailActivity).toBundle())
    }

    private fun showMoreImagesContent(imagesItems: List<ProblemImagesEntity>) {
        bind.moreImagesSectionRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val moreImagesAdapter = LibraryDetailImgAdapter(imagesItems as ArrayList<ProblemImagesEntity>)
        bind.moreImagesSectionRv.adapter = moreImagesAdapter

        moreImagesAdapter.setOnItemClickCallback(object: LibraryDetailImgAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProblemImagesEntity, animBundle: Bundle?) {
                if (animBundle != null) {
                    seeZoomedImg(data, animBundle)
                }
            }
        })
    }

    private fun seeZoomedImg(images: ProblemImagesEntity, bundle: Bundle) {
        val imagesList =
            images.apply {
                ProblemImagesEntity(
                    id,
                    description,
                    imagePic,
                    problemType,
                    createdAt
                )
            }

        val zoomImgIntent = Intent(this@LibraryDetailActivity, ImageShowActivity::class.java)
        zoomImgIntent.putExtra(ImageShowActivity.EXTRA_PIC, imagesList.imagePic)
        zoomImgIntent.putExtra(ImageShowActivity.DIRECTED_FROM_INFORMATIVE_SECTIONS, true)

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
                    name,
                    thumbnailPic,
                    bodyType,
                    problemSeverity,
                    expertName,
                    expertSpecialization,
                    verifiedAt,
                    expertImage,
                    contentHeader,
                    content,
                    createdAt,
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