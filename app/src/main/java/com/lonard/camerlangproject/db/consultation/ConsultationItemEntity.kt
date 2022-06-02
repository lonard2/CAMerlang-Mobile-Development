package com.lonard.camerlangproject.db.consultation

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "consultation_item")
data class ConsultationItemEntity (
): Parcelable