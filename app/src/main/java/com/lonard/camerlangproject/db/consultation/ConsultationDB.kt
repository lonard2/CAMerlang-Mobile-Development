package com.lonard.camerlangproject.db.consultation

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ConsultationDB: RoomDatabase() {

    abstract fun consultDao(): ConsultationDao

    companion object {
        @Volatile
        private var INSTANCE: ConsultationDB? = null

        @JvmStatic
        fun getConsultationDatabase(context: Context): ConsultationDB {
            if (INSTANCE == null) {
                synchronized(ConsultationDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ConsultationDB::class.java, "camerlang_consultation_db")
                        .build()
                }
            }
            return INSTANCE as ConsultationDB
        }
    }

}