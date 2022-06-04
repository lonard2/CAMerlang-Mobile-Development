package com.lonard.camerlangproject.db.library

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEntryToDb(libraryItem: List<LibraryContentEntity>?)

    @Query("SELECT * FROM library_contents")
    fun retrieveAllEntries(): LiveData<LibraryContentEntity>
}