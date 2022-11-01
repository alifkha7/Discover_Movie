package com.hirocode.movie.ui.genres

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hirocode.movie.adapter.GenreListAdapter
import com.hirocode.movie.databinding.ActivityGenresBinding
import com.hirocode.movie.databinding.ItemGenreBinding
import com.hirocode.movie.network.GenresItem
import com.hirocode.movie.ui.main.MainActivity

class GenresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenresBinding
    private val genresViewModel: GenresViewModel by viewModels {
        GenresViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Genre"

        getGenres()
    }

    private fun getGenres() {
        binding.rvGenres.layoutManager = LinearLayoutManager(this)
        val adapter = GenreListAdapter()
        binding.rvGenres.adapter = adapter
        genresViewModel.getGenres()
        genresViewModel.genres.observe(this) {
            adapter.submitList(it)
            adapter.setOnClick(object : GenreListAdapter.OnClick{
                override fun onClicked(data: GenresItem, card: ItemGenreBinding) {
                    val genres = Intent(this@GenresActivity, MainActivity::class.java)
                    genres.putExtra("GENRES", data)
                    startActivity(genres)
                }

            })
        }
    }
}