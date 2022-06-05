package com.lonard.camerlangproject.db.library

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntryToDb(libraryItem: List<LibraryContentEntity>?)

    @Query("DELETE * FROM library_contents")
    suspend fun deleteAllLibraries()

    @Query("SELECT * FROM library_contents")
    fun retrieveAllEntries(): LiveData<List<LibraryContentEntity>>
}