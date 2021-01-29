package com.farras.filmfarras.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ActorListResponse(

	@field:SerializedName("cast")
	val cast: List<ActorResponse>,

)

data class ActorResponse(

	@field:SerializedName("character")
	val character: String,

	@field:SerializedName("original_name")
	val originalName: String
)
