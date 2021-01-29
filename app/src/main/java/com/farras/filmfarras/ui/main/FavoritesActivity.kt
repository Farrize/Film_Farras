package com.farras.filmfarras.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.farras.filmfarras.R
import com.farras.filmfarras.ui.favoritemovie.FavoriteMovieFragment
import com.farras.filmfarras.ui.favoritetvshow.FavoriteTvShowFragment
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        sectionsPagerAdapter.addFragmentList(FavoriteMovieFragment(), resources.getString(R.string.favorite_movie))
        sectionsPagerAdapter.addFragmentList(FavoriteTvShowFragment(), resources.getString(R.string.favorite_tvshow))
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

        bottom_navigation.menu.findItem(R.id.bn_favorites).isChecked = true

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bn_cinema -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }


}