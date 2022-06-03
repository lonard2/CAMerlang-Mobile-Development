package com.lonard.camerlangproject.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.FragmentHomepageBinding
import com.lonard.camerlangproject.mvvm.HomepageRepository
import com.lonard.camerlangproject.mvvm.HomepageViewModel
import com.lonard.camerlangproject.mvvm.HomepageViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.HomepageContentAdapter

class HomepageFragment : Fragment() {

    private var _bind: FragmentHomepageBinding? = null
    private val bind get() = _bind!!

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

        bind.apply {
            notificationBtnIcon.setOnClickListener {
                val notificationScreenIntent = Intent(context, NotificationActivity::class.java)
                startActivity(notificationScreenIntent)
            }
        }

        homeViewModel.getSections().observe {
            homeViewModel.getArticlesData()
            showSections(sectionList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showSections(sectionList: List<SectionItem>) {
        bind.sectionContentHomepageRv.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)

        val homeSectionAdapter = HomepageContentAdapter(sectionList as ArrayList<HomepageResponseItem>)
        bind.sectionContentHomepageRv.adapter = homeSectionAdapter
    }

}