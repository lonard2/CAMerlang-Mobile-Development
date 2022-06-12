package com.lonard.camerlangproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lonard.camerlangproject.db.consultation.ConsultationDao
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.db.consultation.ExpertEntity
import com.lonard.camerlangproject.db.consultation.DetectionResultEntity
import com.lonard.camerlangproject.db.homepage.*
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.db.library.LibraryDao
import com.lonard.camerlangproject.db.library.ProblemImagesEntity

@Database(entities =
    [LibraryContentEntity::class, ArticleEntity::class,
    ProductEntity::class, ConsultationItemEntity::class, ProblemImagesEntity::class,
    NotificationContentEntity::class, NotificationCatEntity::class,
    ExpertEntity::class, DetectionResultEntity::class],
    version = 3, exportSchema = false)
abstract class AppDB: RoomDatabase() {

    abstract fun libraryDao(): LibraryDao
    abstract fun consultationDao(): ConsultationDao
    abstract fun homepageDao(): HomepageDao

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