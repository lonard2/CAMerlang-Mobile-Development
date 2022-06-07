package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HomepageViewModel(private val homeRepo: HomepageRepository): ViewModel() {

    fun getArticlesData() = homeRepo.retrieveArticles()

}