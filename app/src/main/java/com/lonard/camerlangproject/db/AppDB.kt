package com.lonard.camerlangproject.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lonard.camerlangproject.db.consultation.ConsultationDao
import com.lonard.camerlangproject.db.library.LibraryDao

abstract class AppDB: RoomDatabase() {

    abstract fun libraryDao(): LibraryDao
    abstract fun consultationDao(): ConsultationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        @JvmStatic
        fun getAppDatabase(context: Context): AppDB {
            if (INSTANCE == null) {
                synchronized(AppDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDB::class.java, "camerlang_app_db")
                        .build()
                }
            }
            return INSTANCE as AppDB
        }
    }

}