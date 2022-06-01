package com.lonard.camerlangproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeBinding
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeMainBinding
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showCategoryItem(categoryItems: List<CategoryItem>) {
        bind.libCategoryListRv.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val categoryAdapter = LibraryDetailMoreAdapter(categoryItems as ArrayList<LibraryResponseItem>)
        bind.libCategoryListRv.adapter = categoryAdapter
    }

    private fun showAlphabetItemContent(alphabetEntryItems: List<EntryItem>) {
        bind.libAlphabetListRv.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val alphabetItemAdapter = LibraryDetailMoreAdapter(alphabetEntryItems as ArrayList<LibraryResponseItem>)
        bind.libAlphabetListRv.adapter = alphabetItemAdapter
    }

}