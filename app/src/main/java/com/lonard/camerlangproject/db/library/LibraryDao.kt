package com.lonard.camerlangproject.db.library

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lonard.camerlangproject.db.homepage.ProductEntity

@Dao
interface LibraryDao {

    // individual library entries CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntryToDb(libraryItem: List<LibraryContentEntity>?)

    @Query("SELECT * FROM library_contents")
    fun retrieveAllEntries(): LiveData<List<LibraryContentEntity>>

    @Query("SELECT * FROM library_contents WHERE entry_name LIKE '%' || :q || '%'")
    fun retrieveAllEntriesForSpecificQuery(q: String): LiveData<List<LibraryContentEntity>>

    // library entry images (more images) CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImagesToDb(imageItem: List<ProblemImagesEntity>)

    @Query("SELECT * FROM problem_images")
    fun retrieveAllImagesForIndividualLibEntry(): LiveData<List<ProblemImagesEntity>>

    // skincare products CRUD operations

    @Query("SELECT * FROM skincare_products")
    fun retrieveProducts(): LiveData<List<ProductEntity>>
}