package com.farras.filmfarras.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farras.filmfarras.R
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.item_cv_movie.view.*

class MovieAdapter(private val callback: MovieFragmentCallback) : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cv_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies)
        }
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieEntity) {
            with(itemView) {
                Glide.with(context)
                    .load(resources.getString(R.string.base_url_poster, movie.poster))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(img_item_poster)
                tv_item_title.text = movie.title
                tv_item_description.text = movie.storyline
                tv_item_rating_score.text = movie.score.toString()
                img_item_share.setOnClickListener{ callback.onShareClick(movie) }
                setOnClickListener{
                    val intent = Intent(context, DetailMovieActivity::class.java).apply {
                        putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movie.id)
                    }
                    context.startActivity(intent)
                }

            }
        }
    }
}