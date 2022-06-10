package com.lonard.camerlangproject.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.di.DI
import java.lang.IllegalArgumentException

class ConsultationViewModelFactory private constructor(private val consultationRepo: ConsultationRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConsultationViewModel::class.java)) {
            return ConsultationViewModel(consultationRepo) as T
        }

        throw IllegalArgumentException("ConsultationViewModel can't be found on this application instance!")
    }

    companion object {

        @Volatile
        private var INSTANCE: ConsultationViewModelFactory? = null

        @JvmStatic
        fun getFactory(context: Context): ConsultationViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConsultationViewModelFactory(DI.provideRepositoryToConsultationPart(context))
            }.also { INSTANCE = it }

    }
}