package com.farras.filmfarras.data.source.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.farras.filmfarras.data.NetworkBoundResource
import com.farras.filmfarras.data.source.local.LocalDataSource
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.remote.ApiResponse
import com.farras.filmfarras.data.source.remote.RemoteDataSource
import com.farras.filmfarras.data.source.remote.response.ActorResponse
import com.farras.filmfarras.data.source.remote.response.MovieResponse
import com.farras.filmfarras.data.source.remote.response.TvShowResponse
import com.farras.filmfarras.utils.AppExecutors
import com.farras.filmfarras.vo.Resource

class FakeCinemaRepository constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors)
    : CinemaDataSource {

    override fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieList(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> = remoteDataSource.getMovieList()

            override fun saveCallResult(movieResponses: List<MovieResponse>) {
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

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieDetail(id)

            override fun shouldFetch(data: MovieEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> = remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(response: MovieResponse) {
                val movieList = ArrayList<MovieEntity>()
                val movie = MovieEntity(response.id,
                        response.title,
                        response.releaseDate,
                        response.voteAverage,
                        response.overview,
                        false,
                        response.posterPath)
                movieList.add(movie)

                localDataSource.insertMovies(movieList)
            }

        }.asLiveData()
    }

    override fun getMovieActorList(id: Int): LiveData<Resource<List<ActorEntity>>> {
        return object : NetworkBoundResource<List<ActorEntity>, List<ActorResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<ActorEntity>> = localDataSource.getMovieActorList(id)

            override fun shouldFetch(data: List<ActorEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ActorResponse>>> = remoteDataSource.getMovieActors(id)

            override fun saveCallResult(actorResponses: List<ActorResponse>) {
                val actorList = ArrayList<ActorEntity>()
                for (response in actorResponses) {
                    val actor = ActorEntity(response.originalName, response.character, id)
                    actorList.add(actor)
                }

                localDataSource.insertActors(actorList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovieList(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovieList(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) = appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state)}

    override fun getTvShowList(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShowList(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> = remoteDataSource.getTvShowList()

            override fun saveCallResult(tvShowResponses: List<TvShowResponse>) {
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

                localDataSource.insertTvShows(tvShowList)
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> = localDataSource.getTvShowDetail(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<TvShowResponse>> = remoteDataSource.getTvShowDetail(id)

            override fun saveCallResult(response: TvShowResponse) {
                val tvShowList = ArrayList<TvShowEntity>()
                val tvShow = TvShowEntity(response.id,
                        response.name,
                        response.firstAirDate,
                        response.voteAverage,
                        response.overview,
                        false,
                        response.posterPath)
                tvShowList.add(tvShow)

                localDataSource.insertTvShows(tvShowList)
            }

        }.asLiveData()
    }

    override fun getTvShowActorList(id: Int): LiveData<Resource<List<ActorEntity>>> {
        return object : NetworkBoundResource<List<ActorEntity>, List<ActorResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<ActorEntity>> = localDataSource.getTvShowActorList(id)

            override fun shouldFetch(data: List<ActorEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ActorResponse>>> = remoteDataSource.getTvShowActors(id)

            override fun saveCallResult(actorResponses: List<ActorResponse>) {
                val actorList = ArrayList<ActorEntity>()
                for (response in actorResponses) {
                    val actor = ActorEntity(response.originalName, response.character, id)
                    actorList.add(actor)
                }

                localDataSource.insertActors(actorList)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShowList(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShowList(), config).build()
    }

    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) = appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShow, state)}

}