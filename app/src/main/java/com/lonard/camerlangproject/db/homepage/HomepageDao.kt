package com.lonard.camerlangproject.db.homepage

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface HomepageDao {

    // articles-related CRUD functions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticletoDb(articleItem: List<ArticleEntity>?)

    @Query("DELETE * FROM articles")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM articles")
    fun retrieveAllArticles(): LiveData<List<ArticleEntity>>

    // skincare products-related CRUD functions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductstoDb(productList: List<ProductEntity>?)

    @Query("DELETE * FROM skincare_products")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM skincare_products WHERE is_popular = true")
    fun retrievePopularProducts(): LiveData<List<ProductEntity>>

    // notification content-related CRUD functions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotificationtoDb(productList: List<NotificationContentEntity>?)

    @Query("DELETE * FROM notifications")
    suspend fun deleteAllNotifications()

    @Query("SELECT * FROM notifications")
    fun retrieveNotificationContents(): LiveData<List<NotificationContentEntity>>

}