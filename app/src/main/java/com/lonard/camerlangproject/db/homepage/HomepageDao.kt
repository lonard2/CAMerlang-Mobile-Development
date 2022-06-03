package com.lonard.camerlangproject.db.homepage

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface HomepageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticletoDb(articleItem: List<ArticleEntity>?)

    @Query("SELECT * FROM articles")
    fun retrieveAllArticles(): LiveData<ArticleEntity>

}