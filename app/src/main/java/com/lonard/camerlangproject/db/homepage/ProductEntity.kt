package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "skincare_products")
data class ProductEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "product_name")
    var name: String? = null,

    @ColumnInfo(name = "product_img")
    var thumbnail: String? = null,

    @ColumnInfo(name = "is_popular")
    var isPopular: Boolean? = null,

    @ColumnInfo(name = "brand_image")
    var brandImg: String? = null,

): Parcelable
