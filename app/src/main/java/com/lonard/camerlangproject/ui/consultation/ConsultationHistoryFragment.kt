package com.lonard.camerlangproject.ui.consultation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonard.camerlangproject.api.ConsultationResponseItem
import com.lonard.camerlangproject.databinding.FragmentConsultationHistoryBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.db.library.LibraryDetailImgEntity
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.ConsultationHistoryItemAdapter

class ConsultationHistoryFragment : Fragment() {

    private var _bind: FragmentConsultationHistoryBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentConsultationHistoryBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consultFactory: ConsultationViewModelFactory = ConsultationViewModelFactory.getFactory(requireContext())
        val consultViewModel: ConsultationViewModel by viewModels {
            consultFactory
        }

        consultViewModel.retrieveConsultationHistories().observe(requireActivity()) { consultation ->
            showSections(consultation)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun showSections(consultHistoryItem: List<ConsultationItemEntity>) {
        bind.consultationHistoryListRv.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)

        val consultHistoryAdapter = ConsultationHistoryItemAdapter(consultHistoryItem as ArrayList<ConsultationItemEntity>)
        bind.consultationHistoryListRv.adapter = consultHistoryAdapter

        consultHistoryAdapter.setOnItemClickCallback(object: ConsultationHistoryItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ConsultationItemEntity) {
                seeConsultationDetail(data)
            }
        })
    }

    private fun seeConsultationDetail(consultations: ConsultationItemEntity) {
        val consultationItems =
            consultations.apply {
                ConsultationItemEntity(
                    id,
                    consultationImg,
                    processedAt,
                    consultationDetections
                )
            }

        val zoomImgIntent = Intent(activity, ConsultationDetailActivity::class.java)
        zoomImgIntent.putExtra(ConsultationDetailActivity.EXTRA_CONSULTATION_DATA, consultationItems)

        startActivity(zoomImgIntent)
    }
}