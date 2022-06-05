package com.lonard.camerlangproject.db.library

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "library_contents")
data class LibraryContentEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "entry_name")
    var name: String? = null,

    @ColumnInfo(name = "entry_img")
    var thumbnailPic: String? = null,

    @ColumnInfo(name = "entry_type")
    var bodyType: String? = null,

    @ColumnInfo(name = "problem_severity")
    var problemSeverity: String? = null,

    @ColumnInfo(name = "entry_header")
    var contentHeader: String? = null,

    @ColumnInfo(name = "entry_content")
    var content: String? = null,

): Parcelable
