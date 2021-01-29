package com.farras.filmfarras.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farras.filmfarras.R
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.viewmodel.ViewModelFactory
import com.farras.filmfarras.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment(), TvShowFragmentCallback {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            val adapter = TvShowAdapter(this)
            isLoading(true)
            viewModel.getTvShows().observe(viewLifecycleOwner, Observer { tvShows ->
                if (tvShows != null) {
                    when (tvShows.status) {
                        Status.LOADING -> progress_bar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progress_bar.visibility = View.GONE
                            adapter.submitList(tvShows.data)
                            adapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            rv_tvshow.layoutManager = LinearLayoutManager(context)
            rv_tvshow.setHasFixedSize(true)
            rv_tvshow.adapter = adapter
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