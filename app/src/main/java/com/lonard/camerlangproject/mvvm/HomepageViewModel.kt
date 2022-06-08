package com.lonard.camerlangproject.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HomepageViewModel(private val homeRepo: HomepageRepository): ViewModel() {

    fun getArticlesData() = homeRepo.retrieveArticles()
    fun getPopularProducts() = homeRepo.retrievePopularProducts()
    fun getNotificationContent() = homeRepo.retrieveNotificationContent()
    fun getNotificationCategories() = homeRepo.retrieveNotificationCategories()
}