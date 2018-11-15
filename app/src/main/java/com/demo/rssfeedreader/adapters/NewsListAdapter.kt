package com.demo.rssfeedreader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.rssfeedreader.R
import com.demo.rssfeedreader.data.RssFeedItem
import com.demo.rssfeedreader.databinding.ListNewsItemBinding

/**
 * News list adapter
 */
class NewsListAdapter : ListAdapter<RssFeedItem, NewsListAdapter.ViewHolder>(NewsItemDiff()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(createOnClickListener(item.title), item)
        holder.itemView.tag = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListNewsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(itemTitle: String): View.OnClickListener {
        return View.OnClickListener {
            val bundle = bundleOf("itemTitle" to itemTitle)
            // Navigate to the rss item detail screen
            it.findNavController().navigate(R.id.action_list_to_detail, bundle)
        }
    }

    class ViewHolder(
        private val binding: ListNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind data
         */
        fun bind(listener: View.OnClickListener, item: RssFeedItem) {
            binding.clickListener = listener
            binding.item = item
            binding.executePendingBindings()
        }
    }
}