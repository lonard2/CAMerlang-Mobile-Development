package com.lonard.camerlangproject.api

import com.lonard.camerlangproject.db.homepage.ArticleResponse
import com.lonard.camerlangproject.db.library.LibraryContentResponse
import com.lonard.camerlangproject.db.library.LibraryDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    // Endpoint 1. Retrieve articles (and products) information
    @GET("articles")
    fun retrieveArticles(
        @Query("article_titles") articleTitle: String?,
    ): Call<ArticleResponse>

    // Endpoint 2. Search library
    @GET("libraries/search")
    fun getSearchLibrary(
        @Query("q") q: String
    ): Call<LibraryContentResponse>

    // Endpoint 3. Retrieve library
    @GET("libraries")
    fun retrieveLibrary(
        @Query("library_names") libraryTitles: String?,
    ): Call<LibraryContentResponse>

    // Endpoint 4. Retrieve library details
    @GET("libraries/{id}")
    fun retrieveLibraryDetails(
        @Path("id") id: Int
    ): Call<List<LibraryDataItem>>

}