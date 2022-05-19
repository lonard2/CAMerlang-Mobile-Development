package com.lonard.camerlangproject.db.library

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lonard.camerlangproject.db.consultation.ConsultationDao

abstract class LibraryDB: RoomDatabase() {

    abstract fun libraryDao(): LibraryDao

    companion object {
        @Volatile
        private var INSTANCE: LibraryDB? = null

        @JvmStatic
        fun getConsultationDatabase(context: Context): LibraryDB {
            if (INSTANCE == null) {
                synchronized(LibraryDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        LibraryDB::class.java, "camerlang_library_db")
                        .build()
                }
            }
            return INSTANCE as LibraryDB
        }
    }

}