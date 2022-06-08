package com.lonard.camerlangproject.db.homepage

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface HomepageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticletoDb(articleItem: List<ArticleEntity>?)

    @Query("DELETE * FROM articles")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM articles")
    fun retrieveAllArticles(): LiveData<List<ArticleEntity>>

}