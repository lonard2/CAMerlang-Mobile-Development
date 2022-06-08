package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "skincare_products")
data class NotificationContentEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "notification_message_header")
    var messageHeader: String? = null,

    @ColumnInfo(name = "notification_message_content")
    var messageContent: String? = null,

    @ColumnInfo(name = "notification_type")
    var type: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null,

): Parcelable