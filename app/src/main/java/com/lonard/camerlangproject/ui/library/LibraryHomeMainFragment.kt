package com.lonard.camerlangproject.ui.library

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeMainBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.mvvm.LibraryViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.LibraryHomeItemListAdapter
import java.util.*
import kotlin.collections.ArrayList

class LibraryHomeMainFragment : Fragment() {
    private var _bind: FragmentLibraryHomeMainBinding? = null
    private val bind get() = _bind!!

    private val locale: String = Locale.getDefault().language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentLibraryHomeMainBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val libraryFactory: LibraryViewModelFactory =
            LibraryViewModelFactory.getFactory(requireContext())

        val libraryViewModel: LibraryViewModel by viewModels {
            libraryFactory
        }

        bind.apply {
            libraryViewModel.retrieveLibraryEntriesList().observe(viewLifecycleOwner) { entriesList ->
                if (entriesList != null) {
                    when (entriesList) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE

                            val entries = entriesList.data

                            showAlphabetItemContent(entries)
                        }

                        is DataLoadResult.Failed -> {
                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE

                            Snackbar.make(
                                libContentListRv, when (locale) {
                                    "in" -> {
                                        "Daftar permasalahan kulit di pustaka sedang tidak bisa diakses saat ini. " +
                                                "Silakan cek koneksi Internetmu atau coba lagi ya, setelah beberapa waktu."
                                    }
                                    "en" -> {
                                        "Skin problems listing in the library can't be accessed at this time. " +
                                                "Please check your Internet connection or try again at later time."
                                    }
                                    else -> { "Error in libraries entries retrieval." } },
                                Snackbar.LENGTH_LONG
                            ).show()
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

//    private fun showCategoryItem(categoryItems: List<CategoryItem>) {
//        bind.libCategoryListRv.layoutManager = LinearLayoutManager(requireActivity(),
//            LinearLayoutManager.HORIZONTAL, false)
//
//        val categoryAdapter = LibraryDetailMoreAdapter(categoryItems as ArrayList<LibraryResponseItem>)
//        bind.libCategoryListRv.adapter = categoryAdapter
//    }

        private fun showAlphabetItemContent(alphabetEntryItems: List<LibraryContentEntity>) {
            bind.libContentListRv.layoutManager = GridLayoutManager(requireActivity(), 2)

            val alphabetItemAdapter =
                LibraryHomeItemListAdapter(alphabetEntryItems as ArrayList<LibraryContentEntity>)
            bind.libContentListRv.adapter = alphabetItemAdapter

            alphabetItemAdapter.setOnItemClickCallback(object :
                LibraryHomeItemListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: LibraryContentEntity, animBundle: Bundle?) {
                    if (animBundle != null) {
                        viewEntry(data, animBundle)
                    }
                }
            })
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