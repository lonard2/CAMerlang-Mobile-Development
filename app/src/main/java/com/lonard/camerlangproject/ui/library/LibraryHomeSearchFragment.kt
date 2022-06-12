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
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeSearchBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.mvvm.LibraryViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.LibraryHomeItemListAdapter
import java.util.*
import kotlin.collections.ArrayList

class LibraryHomeSearchFragment : Fragment() {
    private var _bind: FragmentLibraryHomeSearchBinding? = null
    private val bind get() = _bind!!

    private val locale: String = Locale.getDefault().language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentLibraryHomeSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val libraryFactory: LibraryViewModelFactory =
            LibraryViewModelFactory.getFactory(requireContext())

        val libraryViewModel: LibraryViewModel by viewModels {
            libraryFactory
        }

        val query = arguments?.getString(EXTRA_QUERY)

        bind.apply {
            if (query != null) {
                libraryViewModel.retrieveLibraryEntriesListWithSearchQuery(query).observe(viewLifecycleOwner) { searchResult ->
                    when (searchResult) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            val entries = searchResult.data

                            showResultsOnQuery(entries)

                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE
                        }

                        is DataLoadResult.Failed -> {
                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE

                            Snackbar.make(
                                libResultsListRv, when (locale) {
                                    "in" -> {
                                        "Hasil pencarian pustakamu sedang tidak bisa dilakukan ya. " +
                                                "Silakan cek koneksi Internetmu atau coba lagi ya, setelah beberapa waktu."
                                    }
                                    "en" -> {
                                        "Search feature in the library cannot be conducted, yet. " +
                                                "Please check your Internet connection or try again at later time."
                                    }
                                    else -> { "Error in libraries entries search retrieval." } },
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

    private fun showResultsOnQuery(categoryItems: List<LibraryContentEntity>) {
        bind.libResultsListRv.layoutManager = GridLayoutManager(requireActivity(), 2)

        val searchedAdapter = LibraryHomeItemListAdapter(categoryItems as ArrayList<LibraryContentEntity>)
        bind.libResultsListRv.adapter = searchedAdapter

        searchedAdapter.setOnItemClickCallback(object :
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

        val libraryEntryDetailIntent = Intent(context, LibraryDetailActivity::class.java)
        libraryEntryDetailIntent.putExtra(LibraryDetailActivity.EXTRA_DISEASE, libraryEntries)

        startActivity(libraryEntryDetailIntent, bundle)
    }

    companion object {
        const val EXTRA_QUERY = "queryObject"
    }

}