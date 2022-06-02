package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products")
data class ProductEntity (

): Parcelable
