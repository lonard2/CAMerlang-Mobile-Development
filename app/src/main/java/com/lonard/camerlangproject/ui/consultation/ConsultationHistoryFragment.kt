package com.lonard.camerlangproject.ui.consultation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lonard.camerlangproject.databinding.FragmentConsultationHistoryBinding
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
import com.lonard.camerlangproject.ui.rv_adapter.ConsultationHistoryItemAdapter
import java.util.*
import kotlin.collections.ArrayList

class ConsultationHistoryFragment : Fragment() {

    private var _bind: FragmentConsultationHistoryBinding? = null
    private val bind get() = _bind!!

    private val locale: String = Locale.getDefault().language

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

        bind.apply {
            consultViewModel.retrieveAllConsultations().observe(requireActivity()) { consultationList ->
                if (consultationList != null) {
                    when (consultationList) {
                        is DataLoadResult.Loading -> {
                            loadFrame.visibility = View.VISIBLE
                            loadAnimLottie.visibility = View.VISIBLE
                        }

                        is DataLoadResult.Successful -> {
                            val consultations = consultationList.data

                            showConsultations(consultations)

                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE
                        }

                        is DataLoadResult.Failed -> {
                            loadFrame.visibility = View.GONE
                            loadAnimLottie.visibility = View.GONE

                            Snackbar.make(
                                consultationHistoryListRv, when (locale) {
                                    "in" -> {
                                        "Aduh, data histori konsultasi sedang tidak dapat ditampilkan. " +
                                                "Silakan coba lagi ya."
                                    }
                                    "en" -> {
                                        "Ouch, the consultation history data cannot be shown to you. " +
                                                "Please try again."
                                    }
                                    else -> {
                                        "Error in consultation history data retrieval."
                                    }
                                }, Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _bind = null
    }

    private fun showConsultations(consultHistoryItem: List<ConsultationItemEntity>) {
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
                )
            }

        val zoomImgIntent = Intent(activity, ConsultationDetailActivity::class.java)
        zoomImgIntent.putExtra(ConsultationDetailActivity.EXTRA_CONSULTATION_DATA, consultationItems)

        startActivity(zoomImgIntent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
    }
}