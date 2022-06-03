package com.lonard.camerlangproject.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lonard.camerlangproject.di.DI
import java.lang.IllegalArgumentException

class LibraryViewModelFactory private constructor(private val libraryRepo: LibraryRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LibraryViewModelFactory::class.java)) {
            return LibraryViewModelFactory(libraryRepo) as T
        }

        throw IllegalArgumentException("LibraryViewModel can't be found on this application instance!")
    }

    companion object {

        @Volatile
        private var INSTANCE: LibraryViewModelFactory? = null

        @JvmStatic
        fun getFactory(context: Context): LibraryViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LibraryViewModelFactory(DI.provideRepositoryToLibraryPart(context))
            }.also { INSTANCE = it }

    }
}