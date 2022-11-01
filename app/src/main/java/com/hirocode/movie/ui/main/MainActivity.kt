package com.hirocode.movie.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.hirocode.movie.R
import com.hirocode.movie.adapter.LoadingStateAdapter
import com.hirocode.movie.adapter.MovieListAdapter
import com.hirocode.movie.databinding.ActivityMainBinding
import com.hirocode.movie.network.GenresItem
import com.hirocode.movie.ui.genres.GenresActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genres = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("GENRES", GenresItem::class.java)
        } else {
            intent.getParcelableExtra("GENRES")
        }
        getData(genres)
    }

    private fun getData(genres: GenresItem?) {
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        val adapter = MovieListAdapter()
        binding.rvMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.getGenreId(genres?.id)
        mainViewModel.movie.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                startActivity(Intent(this, GenresActivity::class.java))
                true
            }
            else -> true
        }
    }
}