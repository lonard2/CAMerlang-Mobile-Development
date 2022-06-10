package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.consultation.ConsultationDao
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.db.consultation.ExpertEntity
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.HomepageDao

class ConsultationRepository(private val db: AppDB, private val api: ApiInterface) {

    private lateinit var consultDao: ConsultationDao

    fun retrieveExpertsInfo(): LiveData<DataLoadResult<List<ExpertEntity>>> = liveData {

        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveExpertsInfo(null)
            val experts = api.data

            val expertsListDb = experts.map { expert ->
                ExpertEntity(
                    expert.id,
                    expert.name,
                    expert.image,
                    expert.specialization,
                    expert.score,
                    expert.status
                )
            }

            db.consultationDao().deleteAllExperts()
            db.consultationDao().addExpertsToDb(expertsListDb)
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(
                TAG, "Cannot retrieve experts information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<ExpertEntity>>> = consultDao.retrieveAllExperts().map { expertItem ->
            DataLoadResult.Successful(expertItem)
        }
        emitSource(savedData)
    }

    fun retrieveConsultationHistories() {

        db.consultationDao().addNewConsultationToDb(
            ConsultationItemEntity(
                id,
                consultationImg,
                processedAt,
                consultationDetections
            )
        )
    }

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