package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationCatResponse(

	@field:SerializedName("data")
	val data: List<NotificationCatDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class NotificationCatDataItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int
): Parcelable
