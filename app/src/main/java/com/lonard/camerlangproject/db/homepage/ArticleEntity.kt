package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "articles")
data class ArticleEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "article_name")
    var name: String? = null,

    @ColumnInfo(name = "article_img")
    var thumbnailPic: String? = null,

    @ColumnInfo(name = "article_type")
    var type: String? = null,

    @ColumnInfo(name = "article_duration")
    var readDuration: String? = null,

    @ColumnInfo("expert_name")
    val expertName: String,

    @ColumnInfo("expert_specialization")
    val expertSpecialization: String,

    @ColumnInfo("expert_verification_date")
    val expertVerificationDate: String,

    @ColumnInfo("expert_image")
    val expertImage: String,

    @ColumnInfo(name = "header")
    var header: String? = null,

    @ColumnInfo(name = "article_content")
    var content: String? = null,

): Parcelable
