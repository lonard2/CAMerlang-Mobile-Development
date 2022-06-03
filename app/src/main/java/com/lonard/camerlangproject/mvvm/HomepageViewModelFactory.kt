package com.lonard.camerlangproject.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.di.DI
import java.lang.IllegalArgumentException

class HomepageViewModelFactory (private val homepageRepo: HomepageRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomepageViewModelFactory::class.java)) {
            return HomepageViewModelFactory(homepageRepo) as T
        }

        throw IllegalArgumentException("HomepageViewModel can't be found on this application instance!")
    }

    companion object {

        @Volatile
        private var INSTANCE: HomepageViewModelFactory? = null

        @JvmStatic
        fun getFactory(context: Context): HomepageViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomepageViewModelFactory(DI.provideRepositoryToHomepagePart(context))
            }.also { INSTANCE = it }

    }
}