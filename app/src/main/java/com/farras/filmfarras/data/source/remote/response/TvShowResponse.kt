package com.farras.filmfarras.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowListResponse(

	@field:SerializedName("results")
	val results: List<TvShowResponse>

)

data class TvShowResponse(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int

)
