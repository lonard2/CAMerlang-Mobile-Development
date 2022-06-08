package com.lonard.camerlangproject.db.consultation

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "experts")
data class ExpertEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo("expert_name")
    val expertName: String?,

    @ColumnInfo(name = "expert_pic")
    var expertPic: String? = null,

    @ColumnInfo(name = "expert_specialization")
    var expertSpecialization: String? = null,

    @ColumnInfo(name = "expert_score")
    var expertScore: Double? = null,

    @ColumnInfo(name = "expert_status")
    var status: String? = null,

) : Parcelable
