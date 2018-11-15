package com.demo.rssfeedreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.demo.rssfeedreader.databinding.ActivityMainBinding
import com.demo.rssfeedreader.network.RssFeedTask
import java.lang.ref.WeakReference

/**
 * Main activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set up navigation controller
        navController = Navigation.findNavController(this, R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Launch the rss feed task to download last data
        RssFeedTask(WeakReference(this)).execute(RSS_FEED_URL)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Support up navigation with the nav controller
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
