package com.demo.rssfeedreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.rssfeedreader.data.RssFeedItemDao

/**
 * Simple view model factory for RssItemListViewModel
 */
class RssItemListViewModelFactory (
    private val dao: RssFeedItemDao
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RssItemListViewModel(dao) as T
    }

}
