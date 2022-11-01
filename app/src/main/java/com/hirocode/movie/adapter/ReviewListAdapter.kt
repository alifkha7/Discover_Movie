package com.hirocode.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hirocode.movie.databinding.ItemReviewBinding
import com.hirocode.movie.network.ReviewsItem

class ReviewListAdapter :
    PagingDataAdapter<ReviewsItem, ReviewListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder (private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewsItem) {
            binding.tvItemAuthor.text = data.author
            binding.tvItemContent.text = data.content
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewsItem>() {
            override fun areItemsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}