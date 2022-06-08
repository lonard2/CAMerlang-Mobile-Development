package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(

	@field:SerializedName("data")
	val data: List<ProductDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class ProductDataItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("is_popular")
	val isPopular: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("brand")
	val brand: String
): Parcelable
