package com.lonard.camerlangproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.FragmentConsultationHistoryBinding
import com.lonard.camerlangproject.databinding.FragmentHomepageBinding
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

        bind.apply {
            notificationBtnIcon.setOnClickListener {
                val notificationScreenIntent = Intent(context, NotificationActivity::class.java)
                startActivity(notificationScreenIntent)
            }
        }

        // viewmodel placed here
        showSections(sectionList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showSections(sectionList: List<SectionItem>) {
        bind.sectionContentHomepageRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        val homeSectionAdapter = HomepageContentAdapter(sectionList as ArrayList<HomepageResponseItem>)
        bind.sectionContentHomepageRv.adapter = homeSectionAdapter
    }

}