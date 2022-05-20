package com.lonard.camerlangproject.mvvm

import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB

class ConsultationRepository(private val db: AppDB, private val api: ApiInterface) {

    companion object {
        private const val TAG = "Consultation & Chatbot Repository"

        @Volatile
        private var INSTANCE: ConsultationRepository? = null

        fun getRepoInstance(
            db: AppDB,
            api: ApiInterface
        ): ConsultationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConsultationRepository(db, api)
            }.also { INSTANCE = it }
    }

}