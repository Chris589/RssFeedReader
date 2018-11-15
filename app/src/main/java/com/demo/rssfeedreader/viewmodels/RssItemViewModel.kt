package com.demo.rssfeedreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.rssfeedreader.data.RssFeedItem
import com.demo.rssfeedreader.data.RssFeedItemDao

/**
 * Rss Item view model
 */
class RssItemViewModel(id: String, dao: RssFeedItemDao) : ViewModel() {

    private val itemDao: RssFeedItemDao = dao
    private val itemId: String = id

    val item: LiveData<RssFeedItem>

    init {
        item = itemDao.get(itemId)
    }
}