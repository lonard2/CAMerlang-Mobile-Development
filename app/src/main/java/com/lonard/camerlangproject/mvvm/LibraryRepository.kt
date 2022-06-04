package com.lonard.camerlangproject.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lonard.camerlangproject.api.ApiConfig
import com.lonard.camerlangproject.api.ApiInterface
import com.lonard.camerlangproject.db.AppDB
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryContentResponse
import com.lonard.camerlangproject.db.library.LibraryDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryRepository(private val db: AppDB, private val api: ApiInterface) {

    private val _libraryItem = MutableLiveData<List<LibraryDataItem>?>()
    val libraryItem: LiveData<List<LibraryDataItem>?> = _libraryItem

    private val _libraryDetails = MutableLiveData<LibraryContentEntity>()
    private val libraryDetails: LiveData<LibraryContentEntity> = _libraryDetails

    private val _load = MutableLiveData<Boolean>()
    val load: LiveData<Boolean> = _load

    fun searchLibrary(query: String) {
        _load.value = true

        val api = this.api.getSearchLibrary(query)

        api.enqueue(object: Callback<LibraryContentResponse> {
            override fun onResponse(
                call: Call<LibraryContentResponse>,
                response: Response<LibraryContentResponse>
            ) {
                _load.value = false

                if (response.isSuccessful) {
                    val libraryList = response.body()?.data
                    _libraryItem.value = libraryList
                } else {
                    Log.e(TAG, "Terjadi gagal respon dari server API aplikasi.")
                }
            }

            override fun onFailure(call: Call<LibraryContentResponse>, t: Throwable) {
                Log.e(TAG, "Tidak dapat menerima data library dari server API aplikasi.")
            }

        })
    }

    fun retrieveLibrary() {
        _load.value = true
        val api = this.api.retrieveLibrary(null)

        api.enqueue(object: Callback<LibraryContentResponse> {
            override fun onResponse(
                call: Call<LibraryContentResponse>,
                response: Response<LibraryContentResponse>
            ) {
                _load.value = false

                if (response.isSuccessful) {
                    val libraryResponses = response.body()?.data
                    _libraryItem.value = libraryResponses

                    val libraryItemDb = libraryResponses?.map { library ->
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

                    db.libraryDao().addEntryToDb(libraryItemDb)
                } else {
                    Log.e(TAG, "Terjadi gagal respon dari server API aplikasi.")
                }
            }

            override fun onFailure(call: Call<LibraryContentResponse>, t: Throwable) {
                _load.value = false
                Log.e(TAG, "Tidak dapat menerima data library dari server API aplikasi.")
            }
        })
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