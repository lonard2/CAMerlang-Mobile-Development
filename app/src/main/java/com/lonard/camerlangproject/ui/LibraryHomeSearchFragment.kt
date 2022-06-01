package com.lonard.camerlangproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeMainBinding
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeSearchBinding
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter

class LibraryHomeSearchFragment : Fragment() {

    private var _bind: FragmentLibraryHomeSearchBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentLibraryHomeSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showResultsOnQuery(categoryItems: List<CategoryItem>) {
        bind.libResultsListRv.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)

        val categoryAdapter = LibraryDetailMoreAdapter(categoryItems as ArrayList<LibraryResponseItem>)
        bind.libResultsListRv.adapter = categoryAdapter

        val query = arguments?.getString(EXTRA_QUERY)

        LibraryViewModel.searchLibrary(query).observe(viewLifecycleOwner) { //result ->
//            when (result) {
//                Loading -> {
//
//                }
//                Success -> {
//
//                }
//                Error -> {
//
//                }
//            }
        }
    }

    companion object {
        const val EXTRA_QUERY = "queryObject"
    }

}