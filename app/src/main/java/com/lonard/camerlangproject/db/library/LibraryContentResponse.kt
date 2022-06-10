package com.lonard.camerlangproject.db.library

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryContentResponse(

	@field:SerializedName("data")
	val data: List<LibraryContentDataItem>,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class LibraryContentDataItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("content_header")
	val contentHeader: String,

	@field:SerializedName("expert_specialization")
	val expertSpecialization: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("problem_severity")
	val problemSeverity: String,

	@field:SerializedName("expert_verification_date")
	val expertVerificationDate: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("expert_name")
	val expertName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("body_type")
	val bodyType: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("expert_image")
	val expertImage: String,

): Parcelable
