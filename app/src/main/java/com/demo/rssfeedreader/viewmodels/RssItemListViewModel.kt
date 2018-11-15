package com.demo.rssfeedreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.rssfeedreader.data.RssFeedItem
import com.demo.rssfeedreader.data.RssFeedItemDao

/**
 * Rss item list view model
 */
class RssItemListViewModel(dao: RssFeedItemDao) : ViewModel() {

    private val itemDao: RssFeedItemDao = dao

    val items: LiveData<List<RssFeedItem>>

    init {
        items = itemDao.getAll()
    }

}