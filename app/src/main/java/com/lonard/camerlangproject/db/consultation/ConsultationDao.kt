package com.lonard.camerlangproject.db.consultation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConsultationDao {

    // CRUD functions related to experts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExpertsToDb(expertItem: List<ExpertEntity>?)

    @Query("DELETE FROM experts")
    suspend fun deleteAllExperts()

    @Query("SELECT * FROM experts")
    fun retrieveAllExperts(): LiveData<List<ExpertEntity>>

    // CRUD functions related to consultation items (including scanner-retrieved ones)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewConsultationToDb(consultItem: ConsultationItemEntity)

    @Query("DELETE FROM consultation_items")
    suspend fun deleteAllConsultation()

    @Query("SELECT * FROM consultation_items")
    fun retrieveAllConsultationData(): LiveData<List<ConsultationItemEntity>>

    @Query("SELECT * FROM consultation_items WHERE consultation_img = :img")
    fun retrieveSpecificConsultationData(img: String): LiveData<ConsultationItemEntity>

    // CRUD functions related to detection results of a picture
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewDetectionResultSetToDb(detectionItem: DetectionResultEntity?)

    @Query("DELETE FROM detection_results")
    suspend fun deleteAllDetectionResults()

    @Query("SELECT * FROM detection_results")
    fun retrieveAllDetectionResults(): LiveData<List<DetectionResultEntity>>

    @Query("SELECT * FROM detection_results WHERE problem_id = :problemId")
    fun retrieveSpecificDetectionResultData(problemId: Int): LiveData<List<DetectionResultEntity>>

}