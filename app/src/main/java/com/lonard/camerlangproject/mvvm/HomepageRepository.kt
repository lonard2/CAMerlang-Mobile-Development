package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map

import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.consultation.ConsultationDao
import com.lonard.camerlangproject.db.homepage.*
import com.lonard.camerlangproject.db.library.LibraryDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomepageRepository(private val db: AppDB,
                         private val api: ApiInterface) {

    private lateinit var homeDao: HomepageDao

    fun retrieveArticles(): LiveData<DataLoadResult<List<ArticleEntity>>> = liveData {

        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveArticles(null)
            val articles = api.data

            val articlesListDb = articles.map { article ->
                ArticleEntity(
                    article.id,
                    article.createdAt,
                    article.title,
                    article.thumbnail,
                    article.type,
                    article.readDuration,
                    article.expertName,
                    article.expertSpecialization,
                    article.expertVerificationDate,
                    article.expertImage,
                    article.contentHeader,
                    article.content
                )
            }

            db.homepageDao().deleteAllArticles()
            db.homepageDao().addArticletoDb(articlesListDb)
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve articles information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<ArticleEntity>>> = homeDao.retrieveAllArticles().map { articleItem ->
            DataLoadResult.Successful(articleItem)
        }
        emitSource(savedData)
    }

    fun retrievePopularProducts(): LiveData<DataLoadResult<List<ProductEntity>>> = liveData {

        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveProductsInformation(null)
            val products = api.data

            val productsListDb = products.map { product ->
                ProductEntity(
                    product.id,
                    product.name,
                    product.image,
                    product.isPopular,
                    product.brand,
                )
            }

            db.homepageDao().deleteAllProducts()
            db.homepageDao().addProductstoDb(productsListDb)
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve products information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<ProductEntity>>> = homeDao.retrievePopularProducts().map { productItem ->
            DataLoadResult.Successful(productItem)
        }
        emitSource(savedData)
    }

    fun retrieveNotificationContent(): LiveData<DataLoadResult<List<NotificationContentEntity>>> = liveData {

        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveNotificationContent()
            val notificationContent = api.data

            val notificationContentDb = notificationContent.map { notification ->
                NotificationContentEntity(
                    notification.id,
                    notification.messageHeader,
                    notification.messageContent,
                    notification.type,
                    notification.createdAt,
                    notification.updatedAt
                )
            }

            db.homepageDao().deleteAllNotifications()
            db.homepageDao().addNotificationtoDb(notificationContentDb)
        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve notification content information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<NotificationContentEntity>>> = homeDao.retrieveNotificationContents().map { notificationContent ->
            DataLoadResult.Successful(notificationContent)
        }
        emitSource(savedData)
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