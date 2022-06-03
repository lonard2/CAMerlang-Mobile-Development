package com.lonard.camerlangproject.api

import com.lonard.camerlangproject.db.homepage.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    // Endpoint 1. Retrieve articles (and products) information
    @GET("register")
    fun retrieveArticles(
        @Query("article_titles") articleTitle: String?,
    ): Call<ArticleResponse>

}