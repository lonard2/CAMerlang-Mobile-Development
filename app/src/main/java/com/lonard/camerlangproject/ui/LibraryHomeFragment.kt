package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.LibraryResponseItem
import com.lonard.camerlangproject.databinding.FragmentHomepageBinding
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeBinding
import com.lonard.camerlangproject.ui.rv_adapter.LibraryDetailMoreAdapter

class LibraryHomeFragment : Fragment() {
    private var _bind: FragmentLibraryHomeBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentLibraryHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showCategoryItem(categoryItems: List<CategoryItem>) {
        bind.libAlphabetListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val categoryAdapter = LibraryDetailMoreAdapter(categoryItems as ArrayList<LibraryResponseItem>)
        bind.libAlphabetListRv.adapter = categoryAdapter
    }

    private fun showAlphabetItemContent(alphabetEntryItems: List<EntryItem>) {
        bind.libAlphabetListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val alphabetItemAdapter = LibraryDetailMoreAdapter(alphabetEntryItems as ArrayList<LibraryResponseItem>)
        bind.libAlphabetListRv.adapter = alphabetItemAdapter
    }
}