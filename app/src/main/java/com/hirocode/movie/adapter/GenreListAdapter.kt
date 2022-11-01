package com.hirocode.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hirocode.movie.databinding.ItemGenreBinding
import com.hirocode.movie.network.GenresItem

class GenreListAdapter :
    ListAdapter<GenresItem, GenreListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    private var onClick: OnClick? = null

    fun setOnClick(onClick: OnClick){
        this.onClick = onClick
    }

    inner class MyViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenresItem) {
            binding.tvItemName.text = data.name
            binding.root.setOnClickListener {
                onClick?.onClicked(data, binding)
            }
        }

    }

    interface OnClick{
        fun onClicked(data: GenresItem, card: ItemGenreBinding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenresItem>() {
            override fun areItemsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}