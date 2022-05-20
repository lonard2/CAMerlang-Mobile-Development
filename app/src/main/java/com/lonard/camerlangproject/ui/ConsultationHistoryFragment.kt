package com.lonard.camerlangproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lonard.camerlangproject.databinding.FragmentConsultationHistoryBinding

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
}