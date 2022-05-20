package com.lonard.camerlangproject.mvvm

import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB

class LibraryRepository(private val db: AppDB, private val api: ApiInterface) {

    companion object {
        private const val TAG = "Library Repository"

        @Volatile
        private var INSTANCE: LibraryRepository? = null

        fun getRepoInstance(
            db: AppDB,
            api: ApiInterface
        ): LibraryRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LibraryRepository(db, api)
            }.also { INSTANCE = it }
    }
}