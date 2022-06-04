package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.ViewModel

class LibraryViewModel(private val libraryRepo: LibraryRepository): ViewModel() {

    val loading = libraryRepo.load

    fun searchLibrary(query: String) = libraryRepo.searchLibrary(query)

    fun retrieveLibrary() = libraryRepo.retrieveLibrary()

    fun getLibrary() = libraryRepo.libraryItem
}