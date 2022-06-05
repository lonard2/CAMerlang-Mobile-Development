package com.lonard.camerlangproject.di

import android.content.Context
import com.lonard.camerlangproject.api.ApiConfig
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.mvvm.ConsultationRepository
import com.lonard.camerlangproject.mvvm.HomepageRepository
import com.lonard.camerlangproject.mvvm.LibraryRepository

object DI {
    private val api = ApiConfig.getApiProduct()

    fun provideRepositoryToHomepagePart(context: Context): HomepageRepository {
        val db = AppDB.getAppDatabase(context)

        return HomepageRepository.getRepoInstance(db, api)
    }

    fun provideRepositoryToConsultationPart(context: Context): ConsultationRepository {
        val db = AppDB.getAppDatabase(context)

        return ConsultationRepository.getRepoInstance(db, api)
    }

    fun provideRepositoryToLibraryPart(context: Context): LibraryRepository {
        val db = AppDB.getAppDatabase(context)

        return LibraryRepository.getRepoInstance(db, api)
    }
}