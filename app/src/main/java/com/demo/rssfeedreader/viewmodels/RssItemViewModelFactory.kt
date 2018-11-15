package com.demo.rssfeedreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.rssfeedreader.data.RssFeedItemDao

/**
 * Simple view model factory for RssItemViewModel
 */
class RssItemViewModelFactory (
    private val dao: RssFeedItemDao,
    private val itemTitle: String
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RssItemViewModel(itemTitle, dao) as T
    }

}