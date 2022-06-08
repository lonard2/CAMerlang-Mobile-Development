package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationContentResponse(

	@field:SerializedName("data")
	val data: List<NotificationDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class NotificationDataItem(

	@field:SerializedName("message_content")
	val messageContent: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("message_header")
	val messageHeader: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String
): Parcelable
