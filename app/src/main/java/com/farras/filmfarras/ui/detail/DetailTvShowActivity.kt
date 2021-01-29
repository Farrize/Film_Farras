package com.farras.filmfarras.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farras.filmfarras.R
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.viewmodel.ViewModelFactory
import com.farras.filmfarras.vo.Status
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import kotlinx.android.synthetic.main.content_detail_tv_show.*



class DetailTvShowActivity : AppCompatActivity() {

    internal lateinit var viewModel: DetailTvShowViewModel
    private var menu: Menu? = null

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val tvShowId = extras.getInt(EXTRA_TV_SHOW_ID)
            if (tvShowId != null) {
                viewModel.setSelectedTvShow(tvShowId)

                viewModel.getTvShowActorList().observe(this, Observer { actorsResource ->
                    if (actorsResource != null) {
                        when (actorsResource.status) {
                            Status.LOADING -> progress_bar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (actorsResource.data != null) {
                                progress_bar.visibility = View.GONE
                                populateActorList(actorsResource.data)
                            }
                            Status.ERROR -> {
                                progress_bar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

                viewModel.tvShowResource.observe(this, Observer { tvshow ->
                    if (tvshow != null) {
                        when (tvshow.status) {
                            Status.LOADING -> progress_bar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (tvshow.data != null) {
                                progress_bar.visibility = View.GONE
                                populateTvShow(tvshow.data)
                            }
                            Status.ERROR -> {
                                progress_bar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        Glide.with(this)
            .load(resources.getString(R.string.base_url_poster, tvShowEntity.poster))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
            .into(img_tv_show_poster)
        tv_item_rating_score.text = tvShowEntity.score.toString()
        tv_item_title.text = tvShowEntity.title
        tv_item_storyline_description.text = tvShowEntity.storyline
    }

    private fun populateActorList(actorList: List<ActorEntity>) {
        when {
            actorList.size >= 3 -> {
                tv_item_leading_actor_name.text = resources.getString(R.string.stars, actorList[0].name, actorList[0].character)
                tv_item_supporting_actor_name.text = resources.getString(R.string.stars, actorList[1].name, actorList[1].character)
                tv_item_character_actor_name.text = resources.getString(R.string.stars, actorList[2].name, actorList[2].character)
            }
            actorList.size >= 2 -> {
                tv_item_leading_actor_name.text = resources.getString(R.string.stars, actorList[0].name, actorList[0].character)
                tv_item_supporting_actor_name.text = resources.getString(R.string.stars, actorList[1].name, actorList[1].character)
            }
            actorList.isNotEmpty() -> {
                tv_item_leading_actor_name.text = resources.getString(R.string.stars, actorList[0].name, actorList[0].character)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        viewModel.tvShowResource.observe(this, Observer { tvshow ->
            if (tvshow != null) {
                when (tvshow.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (tvshow.data != null) {
                        progress_bar.visibility = View.GONE
                        val state = tvshow.data.favorited
                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
        }
    }

}