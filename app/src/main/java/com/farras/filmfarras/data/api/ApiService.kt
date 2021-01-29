package com.farras.filmfarras.data.api

import com.farras.filmfarras.data.source.remote.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") api_key: String): Response<MovieListResponse>

    @GET("tv/popular")
    suspend fun getTvShows(@Query("api_key") api_key: String): Response<TvShowListResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int, @Query("api_key") api_key: String): Response<MovieResponse>

    @GET("tv/{id}")
    suspend fun getTvShowDetail(@Path("id") id: Int, @Query("api_key") api_key: String): Response<TvShowResponse>

    @GET("movie/{id}/credits")
    suspend fun getMovieActors(@Path("id") id: Int, @Query("api_key") api_key: String): Response<ActorListResponse>

    @GET("tv/{id}/credits")
    suspend fun getTvShowActors(@Path("id") id: Int, @Query("api_key") api_key: String): Response<ActorListResponse>
}