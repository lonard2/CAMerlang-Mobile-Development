package com.lonard.camerlangproject.ui.library

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeBinding

class LibraryHomeFragment : Fragment() {
    private var _bind: FragmentLibraryHomeBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentLibraryHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManagerVar = childFragmentManager
        val searchBar = bind.libSearchBox

        val searchBarTextId = searchBar.context.resources.getIdentifier(androidx.appcompat.R.id.search_src_text.toString(), null, null)
        val searchBarCloseBtn = searchBar.context.resources.getIdentifier(androidx.appcompat.R.id.search_close_btn.toString(), null, null)

        val searchBarText: TextView = searchBar.findViewById(searchBarTextId)
        val searchClear: ImageView = searchBar.findViewById(searchBarCloseBtn)

        val searchBarFont: Typeface = Typeface.createFromAsset(context?.assets, "poppins_regular.ttf")

        fragmentManagerVar.commit {
            replace(R.id.library_home_container, LibraryHomeMainFragment())
        }

        searchLibrary(searchBarText, searchClear, searchBarFont, fragmentManagerVar, searchBar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun searchLibrary(searchBarText: TextView, searchClear: ImageView, searchBarFont: Typeface,
                              parentFm: FragmentManager, searchBar: SearchView) {
        val myBundle = Bundle()
        val mySearchFragment = LibraryHomeSearchFragment()
        val myMainFragment = LibraryHomeMainFragment()

        searchBarText.typeface = searchBarFont
        searchBarText.textSize = 12F

        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                searchBar.clearFocus()

                myBundle.putString(LibraryHomeSearchFragment.EXTRA_QUERY, q)
                mySearchFragment.arguments = myBundle

                parentFm.commit {
                    replace(R.id.library_home_container, mySearchFragment)
                }

                return true
            }

            override fun onQueryTextChange(q: String?): Boolean {
                return false
            }
        })

        searchClear.setOnClickListener {
            searchBar.setQuery("", false)
            searchBar.clearFocus()
            parentFm.commit {
                replace(R.id.library_home_container, myMainFragment)
            }
        }

    }

}