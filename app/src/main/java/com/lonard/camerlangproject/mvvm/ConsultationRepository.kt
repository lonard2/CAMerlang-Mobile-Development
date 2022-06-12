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
import com.lonard.camerlangproject.db.consultation.DetectionResultEntity
import com.lonard.camerlangproject.db.consultation.ExpertEntity

class ConsultationRepository(private val db: AppDB, private val api: ApiInterface) {

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

        val savedData: LiveData<DataLoadResult<List<ExpertEntity>>> = db.consultationDao().retrieveAllExperts().map { expertItem ->
            DataLoadResult.Successful(expertItem)
        }
        emitSource(savedData)
    }

    fun addNewConsultationEntry(
        consultationImg: String,
        processedAt: String?,
    ): LiveData<DataLoadResult<ConsultationItemEntity>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            val consultData =
                ConsultationItemEntity(
                    0,
                    consultationImg,
                    processedAt
                )

            db.consultationDao().addNewConsultationToDb(consultData)
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(
                TAG, "Cannot save pictures to Room DB." +
                        "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<ConsultationItemEntity>> = db.consultationDao().retrieveSpecificConsultationData(consultationImg).map {
            DataLoadResult.Successful(it)
        }

        emitSource(savedData)
    }

    fun retrieveAllConsultations(): LiveData<DataLoadResult<List<ConsultationItemEntity>>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            db.consultationDao().retrieveAllConsultationData()
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(
                TAG, "Cannot show consultation lists in the Room DB." +
                        "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<ConsultationItemEntity>>> = db.consultationDao().retrieveAllConsultationData().map { consultItem ->
            DataLoadResult.Successful(consultItem)
        }

        emitSource(savedData)

    }

    fun addNewDetectionResultSet(
        label: String,
        percentage: Int
    ): LiveData<DataLoadResult<List<DetectionResultEntity>>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            db.consultationDao().addNewDetectionResultSetToDb(
                DetectionResultEntity(
                    0,
                    label,
                    percentage,
                )
            )
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(
                TAG, "Cannot save detection results to Room DB." +
                        "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<DetectionResultEntity>>> = db.consultationDao().retrieveSpecificDetectionResultData(0).map {
            DataLoadResult.Successful(it)
        }

        emitSource(savedData)
    }

    fun retrieveAllDetections(): LiveData<DataLoadResult<List<DetectionResultEntity>>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            db.consultationDao().retrieveAllDetectionResults()
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(
                TAG, "Cannot show detections results in the Room DB." +
                        "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<DetectionResultEntity>>> = db.consultationDao().retrieveAllDetectionResults().map { detectionItem ->
            DataLoadResult.Successful(detectionItem)
        }

        emitSource(savedData)

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