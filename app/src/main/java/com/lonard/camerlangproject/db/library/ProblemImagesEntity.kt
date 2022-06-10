package com.lonard.camerlangproject.db.library

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "problem_images")
data class ProblemImagesEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "img_desc")
    var description: String? = null,

    @ColumnInfo(name = "img_url")
    var imagePic: String? = null,

    @ColumnInfo(name = "img_problem_type")
    var problemType: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

): Parcelable

