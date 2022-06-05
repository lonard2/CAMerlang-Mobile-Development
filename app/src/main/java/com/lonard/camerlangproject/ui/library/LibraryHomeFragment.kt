package com.lonard.camerlangproject.ui.library

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.FragmentLibraryHomeBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.mvvm.HomepageViewModel
import com.lonard.camerlangproject.mvvm.HomepageViewModelFactory
import com.lonard.camerlangproject.mvvm.LibraryViewModel
import com.lonard.camerlangproject.mvvm.LibraryViewModelFactory

class LibraryHomeFragment : Fragment() {
    private var _bind: FragmentLibraryHomeBinding? = null
    private val bind get() = _bind!!

    private val searchBarTextId = bind.libSearchBox.context.resources.getIdentifier("android:id/search_src_text", null, null)
    private val searchBarText: TextView = bind.libSearchBox.findViewById(searchBarTextId)

    private val searchBarFont: Typeface = Typeface.createFromAsset(context?.assets, "app_fonts/poppins_regular.ttf")

    private val fragmentManagerVar = parentFragmentManager

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

        fragmentManagerVar.commit {
            replace(R.id.fragment_container, LibraryHomeMainFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun searchLibrary() {
        val myBundle = Bundle()
        val mySearchFragment = LibraryHomeSearchFragment()

        searchBarText.typeface = searchBarFont

        val searchBar = bind.libSearchBox

        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                searchBar.clearFocus()

                myBundle.putString(LibraryHomeSearchFragment.EXTRA_QUERY, q)
                mySearchFragment.arguments = myBundle

                fragmentManagerVar.commit {
                    replace(R.id.fragment_container, mySearchFragment)
                }

                return true
            }

            override fun onQueryTextChange(q: String?): Boolean {
                return false
            }
        })

//        val query = searchBar.query?.toString() ?: ""
    }


}