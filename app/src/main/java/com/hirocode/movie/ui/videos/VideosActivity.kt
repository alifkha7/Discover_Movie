package com.hirocode.movie.ui.videos

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.hirocode.movie.BuildConfig.YT_API_KEY
import com.hirocode.movie.databinding.ActivityVideosBinding
import com.hirocode.movie.network.VideosItem

class VideosActivity : YouTubeBaseActivity() {

    lateinit var binding: ActivityVideosBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("VIDEOS", VideosItem::class.java)
        } else {
            intent.getParcelableExtra("VIDEOS")
        }
        binding.youtubePlayer.initialize(YT_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(videos?.key)
                p1?.play()
                p1?.setFullscreen(true)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Snackbar.make(binding.root, "Failed to initialize youtube player", Snackbar.LENGTH_SHORT).show()
            }

        })
    }
}