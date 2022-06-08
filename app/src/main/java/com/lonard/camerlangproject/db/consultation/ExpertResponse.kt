package com.lonard.camerlangproject.db.consultation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpertResponse(

	@field:SerializedName("data")
	val data: List<ExpertDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class ExpertDataItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("score")
	val score: Double,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("specialization")
	val specialization: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
): Parcelable
