package com.farras.filmfarras.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farras.filmfarras.R
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.viewmodel.ViewModelFactory
import com.farras.filmfarras.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieFragmentCallback {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val adapter = MovieAdapter(this)
            viewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> progress_bar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progress_bar.visibility = View.GONE
                            adapter.submitList(movies.data)
                            adapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            rv_movie.layoutManager = LinearLayoutManager(context)
            rv_movie.setHasFixedSize(true)
            rv_movie.adapter = adapter
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

}