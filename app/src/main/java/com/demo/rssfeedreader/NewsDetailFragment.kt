package com.demo.rssfeedreader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.rssfeedreader.databinding.FragmentNewsDetailBinding
import com.demo.rssfeedreader.viewmodels.RssItemListViewModel
import com.demo.rssfeedreader.viewmodels.RssItemListViewModelFactory
import com.demo.rssfeedreader.viewmodels.RssItemViewModel
import com.demo.rssfeedreader.viewmodels.RssItemViewModelFactory
import android.content.Intent
import android.net.Uri

/**
 * News detail fragment
 * Display detail of a news
 * This fragment takes the item title to display in the arguments
 */
class NewsDetailFragment : Fragment() {

    // view model bound in this fragment (rss item)
    private lateinit var viewModel: RssItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get binding and fragment argument
        val binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        val itemTitle = arguments?.getString("itemTitle") ?: return binding.root

        val context = context ?: return binding.root

        // Create the view model to use with this fragment
        val factory = RssItemViewModelFactory(RssFeedDatabase.getInstance(context).getRssFeedItemDao(), itemTitle)
        viewModel = ViewModelProviders.of(this, factory).get(RssItemViewModel::class.java)

        // Observe any changes to the view model data
        viewModel.item.observe(viewLifecycleOwner, Observer {item ->
            item?.let {
                // Bind the data
                binding.item = item
                binding.clickListener = View.OnClickListener {
                    startWebBrowser(item.link)
                }
            }
        })

        return binding.root
    }

    /**
     * Start the web browser on a given url
     * @param url The url to open in the web browser
     */
    fun startWebBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

}