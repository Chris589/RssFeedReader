package com.demo.rssfeedreader.network

import android.app.Activity
import android.os.AsyncTask
import com.demo.rssfeedreader.RssFeedDatabase
import com.demo.rssfeedreader.data.RssFeedChannel
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/**
 * Rss feed task
 * Download rss feed xml and store results in database
 */
class RssFeedTask(private var weakActivity: WeakReference<Activity>) : AsyncTask<String, Void, List<RssFeedChannel>>() {

    override fun doInBackground(vararg urls: String): List<RssFeedChannel> {
        return try {
            loadXmlFromNetwork(urls[0])
        } catch (e: IOException) {
            // Connection error
            return emptyList()
        } catch (e: XmlPullParserException) {
            // Parsing error
            return emptyList()
        }
    }

    override fun onPostExecute(result: List<RssFeedChannel>) {
        // Store rss channel items in DB
        if (result.isNotEmpty()) {
            val feed = result[0]
            Executors.newSingleThreadExecutor().execute {
                weakActivity.get()?.let {
                    RssFeedDatabase.getInstance(it.applicationContext).getRssFeedItemDao().insert(feed.items)
                }
            }
        }
    }

    private fun loadXmlFromNetwork(urlString: String): List<RssFeedChannel> {
        return downloadUrl(urlString)?.use { stream ->
            RssXmlParser().parse(stream)
        } ?: emptyList()
    }

    @Throws(IOException::class)
    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 10000
            connectTimeout = 15000
            requestMethod = "GET"
            doInput = true
            // Starts the query
            connect()
            inputStream
        }
    }

}