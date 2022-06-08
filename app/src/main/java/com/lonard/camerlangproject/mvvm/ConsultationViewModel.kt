package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.ViewModel

class ConsultationViewModel(private val consultRepo: ConsultationRepository): ViewModel() {

    fun retrieveExpertsData() = consultRepo.retrieveExpertsInfo()

}