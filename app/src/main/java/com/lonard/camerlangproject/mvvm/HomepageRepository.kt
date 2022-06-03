package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.lonard.camerlangproject.api.ApiConfig
import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.homepage.ArticleResponse
import com.lonard.camerlangproject.db.homepage.DataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomepageRepository(private val db: AppDB, private val api: ApiInterface) {

    private val _articlesList = MutableLiveData<List<DataItem>?>()
    private val _load = MutableLiveData<Boolean>()

    val articlesList: LiveData<List<DataItem>?> = _articlesList

    val load: LiveData<Boolean> = _load

    fun retrieveArticles() {
        _load.value = true

        val api = ApiConfig.getApiProduct().retrieveArticles(null)

        api.enqueue(object: Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                _load.value = false

                if(response.isSuccessful) {
                    val respondedArticles = response.body()?.data

                    _articlesList.value = respondedArticles

                    val articlesListDb = respondedArticles?.map { article ->
                        ArticleEntity(
                            article.id,
                            article.thumbnail,
                            article.title,
                            article.type,
                            article.readDuration,
                            article.content
                        )
                    }

                    db.homepageDao().addArticletoDb(articlesListDb)

                } else {
                    Log.e(TAG, "Terjadi gagal respon dari server API aplikasi.")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _load.value = false
                Log.e(TAG, "Tidak dapat menerima data artikel dari server API aplikasi.")
            }

        })
    }

    companion object {
        private const val TAG = "Homepage Repository"

        @Volatile
        private var INSTANCE: HomepageRepository? = null

        fun getRepoInstance(
            db: AppDB,
            api: ApiInterface
        ): HomepageRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomepageRepository(db, api)
            }.also { INSTANCE = it }
    }
}