package com.lonard.camerlangproject.db.homepage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleResponse(

	@field:SerializedName("data")
	val data: List<ArticleDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class ArticleDataItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("contentHeader")
	val contentHeader: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("read_duration")
	val readDuration: String,

	@field:SerializedName("content")
	val content: String

): Parcelable
