package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.DataLoadResult
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDao

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
                    library.createdAt,
                    library.name,
                    library.thumbnail,
                    library.bodyType,
                    library.problemSeverity,
                    library.contentHeader,
                    library.content
                )
            }

            db.libraryDao().deleteAllLibraries()
            db.libraryDao().addEntryToDb(libraryItemDb)

        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve library entries information with search query from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<LibraryContentEntity>>> = libraryDao.retrieveAllEntries().map { articleItem ->
            DataLoadResult.Successful(articleItem)
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
                    library.createdAt,
                    library.name,
                    library.thumbnail,
                    library.bodyType,
                    library.problemSeverity,
                    library.contentHeader,
                    library.content
                )
            }

            db.libraryDao().deleteAllLibraries()
            db.libraryDao().addEntryToDb(libraryItemDb)

        } catch (exception: Exception) {
            emit(DataLoadResult.Failed(exception.message.toString()))
            Log.e(TAG, "Cannot retrieve library entries information from application API server." +
                    "Occurred error: ${exception.message.toString()}")
        }

        val savedData: LiveData<DataLoadResult<List<LibraryContentEntity>>> = libraryDao.retrieveAllEntries().map { articleItem ->
            DataLoadResult.Successful(articleItem)
        }
        emitSource(savedData)
    }

//    fun retrieveLibraryEntriesList(): LiveData<DataLoadResult<List<LibraryContentEntity>>> = liveData {
//        emit(DataLoadResult.Loading)
//
//        try {
//            val api = api.retrieveLibrary(null)
//            val libraryEntries = api.data
//
//            val libraryItemDb = libraryEntries.map { library ->
//                LibraryContentEntity(
//                    library.id,
//                    library.createdAt,
//                    library.name,
//                    library.thumbnail,
//                    library.bodyType,
//                    library.problemSeverity,
//                    library.contentHeader,
//                    library.content
//                )
//            }
//
//        } catch (exception: Exception) {
//            emit(DataLoadResult.Failed(exception.message.toString()))
//            Log.e(TAG, "Cannot retrieve specific library entry from application API server." +
//                    "Occurred error: ${exception.message.toString()}")
//        }
//
//        DataLoadResult.Successful(articleItem)
//    }

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