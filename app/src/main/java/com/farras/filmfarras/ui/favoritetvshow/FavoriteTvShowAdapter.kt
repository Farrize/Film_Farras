package com.farras.filmfarras.ui.favoritetvshow

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
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.ui.detail.DetailTvShowActivity
import kotlinx.android.synthetic.main.item_cv_tvshow.view.*

class FavoriteTvShowAdapter(private val callback: FavoriteTvShowFragmentCallback) : PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.TvShowViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowAdapter.TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cv_tvshow, parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShows = getItem(position)
        if (tvShows != null) {
            holder.bind(tvShows)
        }
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShowEntity) {
            with(itemView) {
                Glide.with(context)
                    .load(resources.getString(R.string.base_url_poster, tvShow.poster))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(img_item_poster)
                tv_item_title.text = tvShow.title
                tv_item_description.text = tvShow.storyline
                tv_item_rating_score.text = tvShow.score.toString()
                img_item_share.setOnClickListener{ callback.onShareClick(tvShow) }
                setOnClickListener {
                    val intent = Intent(context, DetailTvShowActivity::class.java).apply {
                        putExtra(DetailTvShowActivity.EXTRA_TV_SHOW_ID, tvShow.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

}