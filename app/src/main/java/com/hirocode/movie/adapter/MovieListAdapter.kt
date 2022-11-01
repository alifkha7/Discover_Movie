package com.hirocode.movie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hirocode.movie.databinding.ItemMovieBinding
import com.hirocode.movie.network.DiscoverMovieItem
import com.hirocode.movie.ui.details.DetailsActivity
import com.hirocode.movie.utils.BASE_IMAGE_URL

class MovieListAdapter :
    PagingDataAdapter<DiscoverMovieItem, MovieListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder (private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DiscoverMovieItem) {
            Glide.with(itemView)
                .load(BASE_IMAGE_URL + data.posterPath)
                .centerCrop()
                .into(binding.imgItemPoster)
            binding.tvItemTitle.text = data.title
            binding.tvItemOverview.text = data.overview
            itemView.setOnClickListener {
                val detail = Intent(itemView.context, DetailsActivity::class.java)
                detail.putExtra("DETAIL", data)
                itemView.context.startActivity(detail)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoverMovieItem>() {
            override fun areItemsTheSame(oldItem: DiscoverMovieItem, newItem: DiscoverMovieItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DiscoverMovieItem, newItem: DiscoverMovieItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}