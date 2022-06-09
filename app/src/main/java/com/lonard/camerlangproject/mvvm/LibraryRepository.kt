package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.homepage.ProductEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDao
import com.lonard.camerlangproject.db.library.MappedProblemImageItem

class LibraryRepository(private val db: AppDB,
                        private val api: ApiInterface) {

    private lateinit var libraryDao: LibraryDao

    fun retrieveLibraryEntriesListWithSearchQuery(q: String): LiveData<DataLoadResult<List<LibraryContentEntity>>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveLibrary(q)
            val libraryEntries = api.data

            val libraryItemDb = libraryEntries.map { library ->
                LibraryContentEntity(
                    library.id,
                    library.name,
                    library.thumbnail,
                    library.bodyType,
                    library.problemSeverity,
                    library.expertName,
                    library.expertSpecialization,
                    library.expertVerificationDate,
                    library.expertImage,
                    library.contentHeader,
                    library.content,
                    library.createdAt,
                    library.mappedProblemImage,
                )
            }

            val libraryImgDb = libraryEntries.map { entry ->
                entry.mappedProblemImage.map { image ->
                    MappedProblemImageItem(
                        image.id,
                        image.imageUrl,
                    )
                }
            }

            db.libraryDao().addEntryToDb(libraryItemDb)

            db.libraryDao().addImagesToDb(libraryImgDb)

        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve library entries information with search query from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<LibraryContentEntity>>> = libraryDao.retrieveAllEntries().map { entryItem ->
            DataLoadResult.Successful(entryItem)
        }
        emitSource(savedData)
    }

    fun retrieveLibraryEntriesList(): LiveData<DataLoadResult<List<LibraryContentEntity>>> = liveData {
        emit(DataLoadResult.Loading)

        try {
            val api = api.retrieveLibrary(null)
            val libraryEntries = api.data

            val libraryItemDb = libraryEntries.map { library ->
                LibraryContentEntity(
                    library.id,
                    library.name,
                    library.thumbnail,
                    library.bodyType,
                    library.problemSeverity,
                    library.expertName,
                    library.expertSpecialization,
                    library.expertVerificationDate,
                    library.expertImage,
                    library.contentHeader,
                    library.content,
                    library.createdAt,
                    library.mappedProblemImage,
                )
            }

            val libraryImgDb = libraryEntries.map { entry ->
                entry.mappedProblemImage.map { image ->
                    MappedProblemImageItem(
                        image.id,
                        image.imageUrl,
                    )
                }
            }

            db.libraryDao().addEntryToDb(libraryItemDb)

            db.libraryDao().addImagesToDb(libraryImgDb)

        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve library entries information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<LibraryContentEntity>>> = libraryDao.retrieveAllEntries().map { entryItem ->
            DataLoadResult.Successful(entryItem)
        }
        emitSource(savedData)
    }

    fun retrieveProductsInfo(): LiveData<DataLoadResult<List<ProductEntity>>> = liveData {

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
            Log.e(
                TAG, "Cannot retrieve products information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<ProductEntity>>> = libraryDao.retrieveProducts().map { productItem ->
            DataLoadResult.Successful(productItem)
        }
        emitSource(savedData)
    }


    companion object {
        private const val TAG = "Library Repository"

        @Volatile
        private var INSTANCE: LibraryRepository? = null

        fun getRepoInstance(
            db: AppDB,
            api: ApiInterface
        ): LibraryRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LibraryRepository(db, api)
            }.also { INSTANCE = it }
    }
}