package com.lonard.camerlangproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.api.HomepageResponseItem
import com.lonard.camerlangproject.databinding.FragmentConsultationHistoryBinding
import com.lonard.camerlangproject.ui.rv_adapter.ConsultationHistoryItemAdapter
import com.lonard.camerlangproject.ui.rv_adapter.HomepageContentAdapter
import com.lonard.camerlangproject.ui.rv_adapter.HomepageContentListAdapter

class ConsultationHistoryFragment : Fragment() {

    private var _bind: FragmentConsultationHistoryBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentConsultationHistoryBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showSections(consultHistoryItem: List<HistoryItem>) {
        bind.consultationHistoryListRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        val consultHistoryAdapter = ConsultationHistoryItemAdapter(consultHistoryItem as ArrayList<ConsultationResponseItem>)
        bind.consultationHistoryListRv.adapter = consultHistoryAdapter

        consultHistoryAdapter.setOnItemClickCallback(object: ConsultationHistoryItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HistoryItem) {

            }
        })
    }
}