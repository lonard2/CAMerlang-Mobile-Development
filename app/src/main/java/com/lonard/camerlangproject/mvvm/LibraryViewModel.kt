package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.ViewModel

class LibraryViewModel(private val libraryRepo: LibraryRepository): ViewModel() {

    fun retrieveLibraryEntriesList() = libraryRepo.retrieveLibraryEntriesList()

    fun retrieveLibraryEntriesListWithSearchQuery(q: String) = libraryRepo.retrieveLibraryEntriesListWithSearchQuery(q)

    fun retrieveProductsInfo() = libraryRepo.retrieveProductsInfo()

    fun retrieveProblemImages(problemType: String) = libraryRepo.retrieveLibraryProblemImages(problemType)
}