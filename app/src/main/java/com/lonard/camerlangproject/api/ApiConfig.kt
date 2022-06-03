package com.lonard.camerlangproject.api

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private const val BASE_URL = "https://camerlang-api-service-hgtivvbcdq-uc.a.run.app/"

        fun getApiProduct(): ApiInterface {

            val interceptLog = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE
                )
            }

            val okClient = OkHttpClient.Builder()
                .addInterceptor(interceptLog)
                .build()

            val rf = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okClient)
                .build()

            return rf.create(ApiInterface::class.java)

        }
    }

}