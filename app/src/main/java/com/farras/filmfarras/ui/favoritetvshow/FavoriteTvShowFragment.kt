package com.farras.filmfarras.ui.favoritetvshow

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
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*

class FavoriteTvShowFragment : Fragment(), FavoriteTvShowFragmentCallback {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

            val adapter = FavoriteTvShowAdapter(this)
            isLoading(true)
            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, Observer { tvShows ->
                adapter.submitList(tvShows)
                adapter.notifyDataSetChanged()
                isLoading(false)
            })

            rv_tvshow_favorite.layoutManager = LinearLayoutManager(context)
            rv_tvshow_favorite.setHasFixedSize(true)
            rv_tvshow_favorite.adapter = adapter
        }
    }

    override fun onShareClick(tvShow: TvShowEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(requireActivity()).apply {
                setType(mimeType)
                setChooserTitle("Bagikan film ini ke teman-teman anda")
                setText(resources.getString(R.string.share_text, tvShow.title))
                startChooser()
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        progress_bar.visibility = if (loading) View.VISIBLE else View.GONE
    }
}