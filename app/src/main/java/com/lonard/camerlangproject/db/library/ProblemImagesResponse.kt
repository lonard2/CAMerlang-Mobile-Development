package com.lonard.camerlangproject.db.library

import com.google.gson.annotations.SerializedName

data class ProblemImagesResponse(

	@field:SerializedName("data")
	val data: List<ProblemImagesItem>,

	@field:SerializedName("status")
	val status: String
)

data class ProblemImagesItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String
)
