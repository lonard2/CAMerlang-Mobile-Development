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

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("content_header")
	val contentHeader: String,

	@field:SerializedName("expert_specialization")
	val expertSpecialization: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("read_duration")
	val readDuration: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("expert_verification_date")
	val expertVerificationDate: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("expert_name")
	val expertName: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("expert_image")
	val expertImage: String
): Parcelable
