package com.lonard.camerlangproject.db.consultation

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "consultation_items")
data class ConsultationItemEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "consultation_id")
    var id: Int?,

    @ColumnInfo("consultation_img")
    val consultationImg: String?,

    @ColumnInfo(name = "processed_at")
    var processedAt: String? = null,

): Parcelable