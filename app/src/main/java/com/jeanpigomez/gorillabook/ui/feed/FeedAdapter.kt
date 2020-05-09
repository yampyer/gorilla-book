package com.jeanpigomez.gorillabook.ui.feed

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.jeanpigomez.gorillabook.R
import com.jeanpigomez.gorillabook.databinding.ItemPostBinding
import com.jeanpigomez.gorillabook.model.Post
import kotlinx.android.synthetic.main.item_post.view.*

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    private lateinit var feed: List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        holder.bind(feed[position])
        feed[position].image?.let { imageUrl ->
            holder.itemView.ivPicture.visibility = View.VISIBLE
            holder.itemView.ivPicture.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl))
                startActivity(holder.itemView.context, browserIntent, null)
            }
        } ?: kotlin.run {
            holder.itemView.ivPicture.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return if (::feed.isInitialized) feed.size else 0
    }

    fun updateFeed(feed: List<Post>) {
        this.feed = feed
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostViewModel()

        fun bind(post: Post) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}
