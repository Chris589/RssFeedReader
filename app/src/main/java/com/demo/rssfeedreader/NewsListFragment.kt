package com.demo.rssfeedreader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.rssfeedreader.adapters.NewsListAdapter
import com.demo.rssfeedreader.databinding.FragmentNewsListBinding
import com.demo.rssfeedreader.viewmodels.RssItemListViewModel
import com.demo.rssfeedreader.viewmodels.RssItemListViewModelFactory

/**
 * News List Fragment
 * Display a list of news
 */
class NewsListFragment : Fragment() {

    private lateinit var viewModel: RssItemListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get binding
        val binding = FragmentNewsListBinding.inflate(inflater, container, false)

        val context = context ?: return binding.root

        // Create the view model to use with this fragment
        val factory = RssItemListViewModelFactory(RssFeedDatabase.getInstance(context).getRssFeedItemDao())
        viewModel = ViewModelProviders.of(this, factory).get(RssItemListViewModel::class.java)

        // Create an adapter for the recycler view
        val adapter = NewsListAdapter()
        binding.newsList.adapter = adapter

        // Observe changes on the view model items
        viewModel.items.observe(viewLifecycleOwner, Observer { items ->
            if (items != null)
                // Bind data to the adapter
                adapter.submitList(items)
        })

        return binding.root
    }

}