package com.lonard.camerlangproject.ui.homepage

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.databinding.FragmentHomepageBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.consultation.ExpertEntity
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.mvvm.*
import com.lonard.camerlangproject.ui.library.LibraryDetailActivity
import com.lonard.camerlangproject.ui.rv_adapter.HomepageArticleContentListAdapter
import com.lonard.camerlangproject.ui.rv_adapter.HomepageExpertContentListAdapter
import com.lonard.camerlangproject.ui.rv_adapter.HomepageLibraryContentListAdapter
import com.lonard.camerlangproject.ui.rv_adapter.HomepageProductContentListAdapter
import java.util.*
import kotlin.collections.ArrayList

class HomepageFragment : Fragment() {

    private var _bind: FragmentHomepageBinding? = null
    private val bind get() = _bind!!

    private val locale: String = Locale.getDefault().language

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentHomepageBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeFactory: HomepageViewModelFactory = HomepageViewModelFactory.getFactory(requireContext())
        val homeViewModel: HomepageViewModel by viewModels {
            homeFactory
        }

        val consultFactory: ConsultationViewModelFactory = ConsultationViewModelFactory.getFactory(requireContext())
        val consultViewModel: ConsultationViewModel by viewModels {
            consultFactory
        }

        val libraryFactory: LibraryViewModelFactory = LibraryViewModelFactory.getFactory(requireContext())
        val libraryViewModel: LibraryViewModel by viewModels {
            libraryFactory
        }

        bind.apply {
            notificationBtnIcon.setOnClickListener {
                val notificationScreenIntent = Intent(context, NotificationActivity::class.java)
                startActivity(notificationScreenIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }

            homeViewModel.getArticlesData().observe(viewLifecycleOwner) { articleList ->
                homeViewModel.getPopularProducts().observe(viewLifecycleOwner) { productList ->
                    libraryViewModel.retrieveLibraryEntriesList().observe(viewLifecycleOwner) { libraryList ->
                        consultViewModel.retrieveExpertsData().observe(viewLifecycleOwner) { expertList ->
                            if (articleList != null) {
                                when (articleList) {
                                    is DataLoadResult.Loading -> {
                                        loadFrame.visibility = View.VISIBLE
                                        loadAnimLottie.visibility = View.VISIBLE
                                    }

                                    is DataLoadResult.Successful -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE

                                        val articles = articleList.data

                                        showArticleSection(articles)
                                    }

                                    is DataLoadResult.Failed -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE

                                        Snackbar.make(
                                            articleOverflowRecyclerview, when (locale) {
                                                "in" -> {
                                                    "Aduh, data artikel tidak bisa ditampilkan. Silakan coba lagi ya."
                                                }
                                                "en" -> {
                                                    "Ouch, the articles data cannot be shown to you. Please try again."
                                                }
                                                else -> {
                                                    "Error in articles data retrieval."
                                                }
                                            }, Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            if (productList != null) {
                                when (productList) {
                                    is DataLoadResult.Loading -> {
                                        loadFrame.visibility = View.VISIBLE
                                        loadAnimLottie.visibility = View.VISIBLE
                                    }

                                    is DataLoadResult.Successful -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE
                                        val products = productList.data

                                        showProductSection(products)
                                    }

                                    is DataLoadResult.Failed -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE

                                        Snackbar.make(
                                            productsOverflowRecyclerview, when (locale) {
                                                "in" -> {
                                                    "Aduh, data produk populer tidak bisa ditampilkan. Silakan coba lagi ya."
                                                }
                                                "en" -> {
                                                    "Ouch, the popular products data cannot be shown to you. Please try again."
                                                }
                                                else -> {
                                                    "Error in popular products data retrieval."
                                                }
                                            },
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            if (libraryList != null) {
                                when (libraryList) {
                                    is DataLoadResult.Loading -> {
                                        loadFrame.visibility = View.VISIBLE
                                        loadAnimLottie.visibility = View.VISIBLE
                                    }

                                    is DataLoadResult.Successful -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE
                                        val entries = libraryList.data

                                        showLibrarySection(entries)
                                    }

                                    is DataLoadResult.Failed -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE

                                        Snackbar.make(
                                            libraryEntriesOverflowRecyclerview, when (locale) {
                                                "in" -> {
                                                    "Aduh, data entri pustaka tidak bisa ditampilkan. Silakan coba lagi ya."
                                                }
                                                "en" -> {
                                                    "Ouch, the library entries data cannot be shown to you. Please try again."
                                                }
                                                else -> {
                                                    "Error in library entries data retrieval."
                                                }
                                            }, Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            if (expertList != null) {
                                when (expertList) {
                                    is DataLoadResult.Loading -> {
                                        loadFrame.visibility = View.VISIBLE
                                        loadAnimLottie.visibility = View.VISIBLE
                                    }

                                    is DataLoadResult.Successful -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE
                                        val expertPeople = expertList.data

                                        showExpertSection(expertPeople)
                                    }

                                    is DataLoadResult.Failed -> {
                                        loadFrame.visibility = View.GONE
                                        loadAnimLottie.visibility = View.GONE

                                        Snackbar.make(
                                            libraryEntriesOverflowRecyclerview, when (locale) {
                                                "in" -> {
                                                    "Aduh, data para ahli kulit tidak bisa ditampilkan. Silakan coba lagi ya."
                                                }
                                                "en" -> {
                                                    "Ouch, the skin experts data cannot be shown to you. Please try again."
                                                }
                                                else -> {
                                                    "Error in skin experts data retrieval."
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showArticleSection(articleList: List<ArticleEntity>) {
        bind.articleOverflowRecyclerview.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val articleOverflowRvAdapter = HomepageArticleContentListAdapter(articleList as ArrayList<ArticleEntity>)
        bind.articleOverflowRecyclerview.adapter = articleOverflowRvAdapter

        articleOverflowRvAdapter.setOnItemClickCallback(object: HomepageArticleContentListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ArticleEntity, animBundle: Bundle?) {
                if (animBundle != null) {
                    viewArticle(data, animBundle)
                }
            }
        })
    }

    private fun viewArticle(articlesModel: ArticleEntity, bundle: Bundle) {
        val articles =
            articlesModel.apply {
                ArticleEntity(
                    id,
                    createdAt,
                    name,
                    thumbnailPic,
                    type,
                    readDuration,
                    expertName,
                    expertSpecialization,
                    expertVerificationDate,
                    expertImage,
                    header,
                    content,
                )
            }

        val articleIntent = Intent(context, ArticleDetailActivity::class.java)
        articleIntent.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, articles)

        startActivity(articleIntent, bundle)
    }

    private fun showProductSection(productList: List<ProductEntity>) {
        bind.productsOverflowRecyclerview.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val productsOverflowRecyclerview = HomepageProductContentListAdapter(productList as ArrayList<ProductEntity>)
        bind.articleOverflowRecyclerview.adapter = productsOverflowRecyclerview
    }

    private fun showLibrarySection(entryList: List<LibraryContentEntity>) {
        bind.libraryEntriesOverflowRecyclerview.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val libraryOverflowRvAdapter = HomepageLibraryContentListAdapter(entryList as ArrayList<LibraryContentEntity>)
        bind.libraryEntriesOverflowRecyclerview.adapter = libraryOverflowRvAdapter

        libraryOverflowRvAdapter.setOnItemClickCallback(object: HomepageLibraryContentListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?) {
                if (animBundle != null) {
                    viewEntry(data, animBundle)
                }
            }
        })
    }

    private fun showExpertSection(expertList: List<ExpertEntity>) {
        bind.expertsOverflowRecyclerview?.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val expertsOverflowAdapter = HomepageExpertContentListAdapter(expertList as ArrayList<ExpertEntity>)
        bind.expertsOverflowRecyclerview?.adapter = expertsOverflowAdapter
    }

    private fun viewEntry(entryModel: LibraryContentEntity, bundle: Bundle) {
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

        val libraryEntryDetailIntent = Intent(context, LibraryDetailActivity::class.java)
        libraryEntryDetailIntent.putExtra(LibraryDetailActivity.EXTRA_DISEASE, libraryEntries)

        startActivity(libraryEntryDetailIntent, bundle)
    }

}