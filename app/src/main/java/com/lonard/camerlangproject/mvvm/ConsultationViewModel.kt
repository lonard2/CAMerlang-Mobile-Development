package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.ViewModel

class ConsultationViewModel(private val consultRepo: ConsultationRepository): ViewModel() {

    fun retrieveExpertsData() = consultRepo.retrieveExpertsInfo()

    fun retrieveAllConsultations() = consultRepo.retrieveAllConsultations()

    fun addConsultationEntry(
        image: String,
        dateTime: String,
    ) = consultRepo.addNewConsultationEntry(image, dateTime)

    fun addDetectionResultData(
        problemId: String,
        label: String,
        percentage: Int
    ) = consultRepo.addNewDetectionResultSet(problemId, label, percentage)

}
