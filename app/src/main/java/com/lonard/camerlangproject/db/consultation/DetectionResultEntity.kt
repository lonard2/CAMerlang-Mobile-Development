package com.lonard.camerlangproject.db.consultation

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "detection_results")
data class DetectionResultEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "problem_id")
    var problemId: Int? = null,

    @ColumnInfo(name = "result_disease")
    var resultDisease: String? = null,

    @ColumnInfo(name = "result_percentage")
    var resultPercentage: Int? = null,

): Parcelable