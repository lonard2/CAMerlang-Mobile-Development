package com.lonard.camerlangproject.mvvm

import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB

class HomepageRepository(private val db: AppDB, private val api: ApiInterface) {

    companion object {
        private const val TAG = "Homepage Repository"

        @Volatile
        private var INSTANCE: HomepageRepository? = null

        fun getRepoInstance(
            db: AppDB,
            api: ApiInterface
        ): HomepageRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomepageRepository(db, api)
            }.also { INSTANCE = it }
    }
}