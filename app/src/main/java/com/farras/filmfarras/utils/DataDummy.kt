package com.farras.filmfarras.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.remote.RemoteDataSource
import com.farras.filmfarras.data.source.remote.response.ActorResponse
import com.farras.filmfarras.data.source.remote.response.MovieResponse
import com.farras.filmfarras.data.source.remote.response.TvShowResponse

object DataDummy {


    private val remoteDataSource = RemoteDataSource.getInstance()

    fun generateDummyMovies(): List<MovieEntity> {
        val movieResponses = LiveDataTestUtil.getValue(getMovieResponses())
        val movieList = ArrayList<MovieEntity>()
        for (response in movieResponses) {
            val movie = MovieEntity(response.id,
                    response.title,
                    response.releaseDate,
                    response.voteAverage,
                    response.overview,
                    false,
                    response.posterPath)
            movieList.add(movie)
        }
        return movieList
    }

    fun generateDummyTvShows(): List<TvShowEntity> {
        val tvShowResponses = LiveDataTestUtil.getValue(getTvShowResponses())
        val tvShowList = ArrayList<TvShowEntity>()
        for (response in tvShowResponses) {
            val tvShow = TvShowEntity(response.id,
                    response.name,
                    response.firstAirDate,
                    response.voteAverage,
                    response.overview,
                    false,
                    response.posterPath)
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun generateDummyMovie(id: Int): MovieEntity {
        val response = LiveDataTestUtil.getValue(getMovieResponse(id))
        return MovieEntity(response.id,
                response.title,
                response.releaseDate,
                response.voteAverage,
                response.overview,
                false,
                response.posterPath)
    }

    fun generateDummyTvShow(id: Int): TvShowEntity {
        val response = LiveDataTestUtil.getValue(getTvShowResponse(id))
        return TvShowEntity(response.id,
                response.name,
                response.firstAirDate,
                response.voteAverage,
                response.overview,
                false,
                response.posterPath)
    }

    fun generateDummyMovieActors(id: Int): List<ActorEntity> {
        val actorResponses = LiveDataTestUtil.getValue(getMovieActorResponses(id))
        val actorList = ArrayList<ActorEntity>()
        for (response in actorResponses) {
            val actor = ActorEntity(response.originalName, response.character, id)
            actorList.add(actor)
        }
        return actorList
    }

    fun generateDummyTvShowActors(id: Int): List<ActorEntity> {
        val actorResponses = LiveDataTestUtil.getValue(getTvShowActorResponses(id))
        val actorList = ArrayList<ActorEntity>()
        for (response in actorResponses) {
            val actor = ActorEntity(response.originalName, response.character, id)
            actorList.add(actor)
        }
        return actorList
    }

    fun generateRemoteDummyMovies(): List<MovieResponse> = LiveDataTestUtil.getValue(getMovieResponses())

    fun generateRemoteDummyMovie(id: Int): MovieResponse = LiveDataTestUtil.getValue(getMovieResponse(id))

    fun generateRemoteDummyMovieActors(id: Int): List<ActorResponse> = LiveDataTestUtil.getValue(getMovieActorResponses(id))

    fun generateRemoteDummyTvShows(): List<TvShowResponse> = LiveDataTestUtil.getValue(getTvShowResponses())

    fun generateRemoteDummyTvShow(id: Int): TvShowResponse = LiveDataTestUtil.getValue(getTvShowResponse(id))

    fun generateRemoteDummyTvShowActors(id: Int): List<ActorResponse> = LiveDataTestUtil.getValue(getTvShowActorResponses(id))

    fun generateInstrumentationDummyMovie(): MovieEntity {
        return MovieEntity(337401,
                "Mulan",
                "2020-09-04",
                7.2,
                "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
                false,
                "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg")
    }

    fun generateInstrumentationDummyTvShow(): TvShowEntity {
        return TvShowEntity(456,
                "The Simpsons",
                "1989-12-16",
                7.8,
                "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
                false,
                "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg")
    }

    private fun getMovieResponses(): LiveData<List<MovieResponse>> {
        val movies = MutableLiveData<List<MovieResponse>>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getMovieList())
        movies.value = data.body
        return movies
    }

    private fun getMovieResponse(id: Int): LiveData<MovieResponse> {
        val movie = MutableLiveData<MovieResponse>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getMovieDetail(id))
        movie.value = data.body
        return movie
    }

    private fun getMovieActorResponses(id: Int): LiveData<List<ActorResponse>> {
        val actors = MutableLiveData<List<ActorResponse>>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getMovieActors(id))
        actors.value = data.body
        return actors
    }

    private fun getTvShowResponses(): LiveData<List<TvShowResponse>> {
        val tvShows = MutableLiveData<List<TvShowResponse>>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getTvShowList())
        tvShows.value = data.body
        return tvShows
    }

    private fun getTvShowResponse(id: Int): LiveData<TvShowResponse> {
        val tvShow = MutableLiveData<TvShowResponse>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getTvShowDetail(id))
        tvShow.value = data.body
        return tvShow
    }

    private fun getTvShowActorResponses(id: Int): LiveData<List<ActorResponse>> {
        val actors = MutableLiveData<List<ActorResponse>>()
        val data = LiveDataTestUtil.getValue(remoteDataSource.getTvShowActors(id))
        actors.value = data.body
        return actors
    }

}