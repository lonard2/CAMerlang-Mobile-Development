package com.lonard.camerlangproject.ui.library

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeBinding
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeMainBinding
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDataItem
import com.lonard.camerlangproject.ui.rv_adapter.HomepageLibraryContentListAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryHomeItemContentAdapter
import com.lonard.camerlangproject.ui.rv_adapter.LibraryHomeItemListAdapter

class LibraryHomeMainFragment : Fragment() {
    private var _bind: FragmentLibraryHomeMainBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentLibraryHomeMainBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.
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
        bind.libContentListRv.layoutManager = GridLayoutManager(requireActivity(),2)

        val alphabetItemAdapter = LibraryHomeItemListAdapter(alphabetEntryItems as ArrayList<LibraryContentEntity>)
        bind.libContentListRv.adapter = alphabetItemAdapter

        alphabetItemAdapter.setOnItemClickCallback(object: LibraryHomeItemListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: LibraryContentEntity) {
                viewEntry(data)
            }
        })
    }

    private fun viewEntry(entryModel: LibraryContentEntity) {
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

        startActivity(libraryEntryDetailIntent)
    }

}