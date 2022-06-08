package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.ViewModel

class LibraryViewModel(private val libraryRepo: LibraryRepository): ViewModel() {

    fun searchLibrary(query: String) = libraryRepo.retrieveLibraryEntriesListWithSearchQuery(query)

    fun retrieveLibraryEntriesList() = libraryRepo.retrieveLibraryEntriesList()

    fun retrieveProductsInfo() = libraryRepo.retrieveProductsInfo()
}