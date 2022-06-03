package com.lonard.camerlangproject.db.homepage

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface HomepageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticletoDb(articleItem: List<ArticleResponse>)

    @Query("SELECT * FROM articles")
    fun retrieveAllArticles(): LiveData<ArticleEntity>

}