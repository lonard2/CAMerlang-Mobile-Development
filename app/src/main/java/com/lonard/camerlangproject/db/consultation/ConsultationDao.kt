package com.lonard.camerlangproject.db.consultation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lonard.camerlangproject.db.homepage.ArticleEntity

@Dao
interface ConsultationDao {

    // CRUD functions related to experts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExpertsToDb(expertItem: List<ExpertEntity>?)

    @Query("DELETE FROM experts")
    suspend fun deleteAllExperts()

    @Query("SELECT * FROM experts")
    fun retrieveAllExperts(): LiveData<List<ExpertEntity>>

}