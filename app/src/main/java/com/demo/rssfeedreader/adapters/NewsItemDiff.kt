package com.demo.rssfeedreader.adapters

import androidx.recyclerview.widget.DiffUtil
import com.demo.rssfeedreader.data.RssFeedItem

/**
 * News item diff
 */
class NewsItemDiff : DiffUtil.ItemCallback<RssFeedItem>() {

    override fun areItemsTheSame(oldItem: RssFeedItem, newItem: RssFeedItem): Boolean {
        return oldItem.pubDate == newItem.pubDate
    }

    override fun areContentsTheSame(oldItem: RssFeedItem, newItem: RssFeedItem): Boolean {
        return oldItem == newItem
    }
}