package com.hirocode.movie.ui.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hirocode.movie.adapter.LoadingStateAdapter
import com.hirocode.movie.adapter.ReviewListAdapter
import com.hirocode.movie.adapter.VideosListAdapter
import com.hirocode.movie.databinding.ActivityDetailsBinding
import com.hirocode.movie.databinding.ItemVideosBinding
import com.hirocode.movie.network.DiscoverMovieItem
import com.hirocode.movie.network.VideosItem
import com.hirocode.movie.ui.videos.VideosActivity
import com.hirocode.movie.utils.BASE_IMAGE_URL
import java.lang.StrictMath.abs

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val detailViewModel: DetailsViewModel by viewModels {
        DetailViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DETAIL", DiscoverMovieItem::class.java)
        } else {
            intent.getParcelableExtra("DETAIL")
        }
        setData(data)
        getVideos(data)
        getReview(data)
    }

    private fun setData(data: DiscoverMovieItem?) {
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.toolbarLayout.title = data?.title
            } else {
                binding.toolbarLayout.title = ""
            }
        }
        Glide.with(this)
            .load(BASE_IMAGE_URL + data?.backdropPath)
            .centerCrop()
            .into(binding.backdrop)
        Glide.with(this)
            .load(BASE_IMAGE_URL + data?.posterPath)
            .centerCrop()
            .into(binding.poster)
        binding.title.text = data?.title
        binding.overview.text = data?.overview
    }

    private fun getVideos(data: DiscoverMovieItem?) {
        binding.rvVideos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = VideosListAdapter()
        binding.rvVideos.adapter = adapter
        detailViewModel.getVideos(data?.id)
        detailViewModel.videos.observe(this) { list ->
            adapter.submitList(list.filter { it.type.contains("Trailer") })
            adapter.setOnClick(object : VideosListAdapter.OnClick{
                override fun onClicked(data: VideosItem, card: ItemVideosBinding) {
                    val videos = Intent(this@DetailsActivity, VideosActivity::class.java)
                    videos.putExtra("VIDEOS", data)
                    startActivity(videos)
                }
            })
        }
    }

    private fun getReview(data: DiscoverMovieItem?) {
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        val adapter = ReviewListAdapter()
        binding.rvReview.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        detailViewModel.getMovieId(data?.id)
        detailViewModel.review.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}