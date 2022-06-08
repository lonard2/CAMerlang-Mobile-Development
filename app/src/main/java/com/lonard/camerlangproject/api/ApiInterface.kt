package com.lonard.camerlangproject.api

import com.lonard.camerlangproject.db.consultation.ExpertResponse
import com.lonard.camerlangproject.db.homepage.ArticleResponse
import com.lonard.camerlangproject.db.homepage.NotificationCatResponse
import com.lonard.camerlangproject.db.homepage.NotificationContentResponse
import com.lonard.camerlangproject.db.homepage.ProductResponse
import com.lonard.camerlangproject.db.library.LibraryContentDataItem
import com.lonard.camerlangproject.db.library.LibraryContentResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    // Endpoint 1. Retrieve articles information
    @GET("articles")
    suspend fun retrieveArticles(
        @Query("article_titles") articleTitle: String?,
    ): ArticleResponse

//    // Endpoint 2b. Search library
//    @GET("libraries/search")
//    suspend fun getLibrarySearch(
//        @Query("q") q: String
//    ): LibraryContentResponse

    // Endpoint 2. Retrieve library
    @GET("libraries")
    suspend fun retrieveLibrary(
        @Query("library_names") libraryEntryTitles: String?,
    ): LibraryContentResponse

    // Endpoint 3. Retrieve library details
    @GET("libraries/{id}")
    suspend fun retrieveLibraryDetails(
        @Path("id") id: Int
    ): LibraryContentDataItem

    // Endpoint 4. Retrieve skincare products information
    @GET("skincare_products")
    suspend fun retrieveProductsInformation(
        @Query("product_names") product: String?,
    ): ProductResponse

    // Endpoint 5. Retrieve notification data
    @GET("notifications")
    suspend fun retrieveNotificationContent(): NotificationContentResponse

    // Endpoint 6. Retrieve notification categories
    @GET("notification_categories")
    suspend fun retrieveNotificationCategories(): NotificationCatResponse

    // Endpoint 7. Retrieve expert informations
    @GET("experts")
    suspend fun retrieveExpertsInfo(
        @Query("name") expertName: String?,
    ): ExpertResponse

}