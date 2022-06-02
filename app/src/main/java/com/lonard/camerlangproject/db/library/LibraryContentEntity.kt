package com.lonard.camerlangproject.db.library

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "library_contents")
data class LibraryContentEntity (
): Parcelable