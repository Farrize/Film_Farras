package com.farras.filmfarras.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farras.filmfarras.BuildConfig
import com.farras.filmfarras.data.api.ApiConfig
import com.farras.filmfarras.data.source.remote.response.ActorResponse
import com.farras.filmfarras.data.source.remote.response.MovieResponse
import com.farras.filmfarras.data.source.remote.response.TvShowResponse
import com.farras.filmfarras.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource()
                }
    }

    fun getMovieList(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getMovies(BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()?.results
                if (result != null) {
                    resultMovies.postValue(ApiResponse.success(result))
                    EspressoIdlingResource.decrement()
                }
            }
        }
        return resultMovies
    }

    fun getTvShowList(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getTvShows(BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()?.results
                if (result != null) {
                    resultTvShows.postValue(ApiResponse.success(result))
                    EspressoIdlingResource.decrement()
                }
            }
        }
        return resultTvShows
    }

    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getMovieDetail(id, BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    resultMovie.postValue(ApiResponse.success(result))
                    EspressoIdlingResource.decrement()
                }
            }
        }
        return resultMovie
    }

    fun getTvShowDetail(id: Int): LiveData<ApiResponse<TvShowResponse>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<TvShowResponse>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getTvShowDetail(id, BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    resultTvShow.postValue(ApiResponse.success(result))
                    EspressoIdlingResource.decrement()
                }
            }
        }
        return resultTvShow
    }

    fun getMovieActors(id: Int): LiveData<ApiResponse<List<ActorResponse>>> {
        EspressoIdlingResource.increment()
        val resultActors = MutableLiveData<ApiResponse<List<ActorResponse>>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getMovieActors(id, BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()?.cast
                if (result != null)
                resultActors.postValue(ApiResponse.success(result))
                EspressoIdlingResource.decrement()
            }
        }
        return resultActors
    }

    fun getTvShowActors(id: Int): LiveData<ApiResponse<List<ActorResponse>>> {
        EspressoIdlingResource.increment()
        val resultActors = MutableLiveData<ApiResponse<List<ActorResponse>>>()
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiConfig.getApiService().getTvShowActors(id, BuildConfig.TOKEN)
            if (response.isSuccessful) {
                val result = response.body()?.cast
                if (result != null) {
                    resultActors.postValue(ApiResponse.success(result))
                    EspressoIdlingResource.decrement()
                }
            }
        }
        return resultActors
    }

}