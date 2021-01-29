package com.farras.filmfarras.ui.favoritemovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farras.filmfarras.R
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*


class FavoriteMovieFragment : Fragment(), FavoriteMovieFragmentCallback {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            val adapter = FavoriteMovieAdapter(this)
            isLoading(true)
            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, Observer { movies ->
                adapter.submitList(movies)
                adapter.notifyDataSetChanged()
                isLoading(false)
            })

            rv_movie_favorite.layoutManager = LinearLayoutManager(context)
            rv_movie_favorite.setHasFixedSize(true)
            rv_movie_favorite.adapter = adapter
        }
    }

    override fun onShareClick(movie: MovieEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(requireActivity()).apply {
                setType(mimeType)
                setChooserTitle(resources.getString(R.string.share_title))
                setText(resources.getString(R.string.share_text, movie.title))
                startChooser()
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        progress_bar.visibility = if (loading) View.VISIBLE else View.GONE
    }
}