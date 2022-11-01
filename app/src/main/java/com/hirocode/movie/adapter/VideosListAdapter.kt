package com.hirocode.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hirocode.movie.databinding.ItemVideosBinding
import com.hirocode.movie.network.VideosItem
import com.hirocode.movie.utils.BASE_THUMBNAIL_URL
import com.hirocode.movie.utils.QUALITY

class VideosListAdapter :
    ListAdapter<VideosItem, VideosListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class MyViewHolder(private val binding: ItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideosItem) {
            Glide.with(itemView.context)
                .load(BASE_THUMBNAIL_URL + data.key + QUALITY)
                .centerCrop()
                .into(binding.thumbnail)
            binding.root.setOnClickListener {
                onClick?.onClicked(data, binding)
            }
        }

    }

    interface OnClick{
        fun onClicked(data: VideosItem, card: ItemVideosBinding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideosItem>() {
            override fun areItemsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}