package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "skincare_products")
data class NotificationCatEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "notification_cat_name")
    var name: String? = null,

    @ColumnInfo(name = "notification_cat_desc")
    var description: String? = null,

    ): Parcelable