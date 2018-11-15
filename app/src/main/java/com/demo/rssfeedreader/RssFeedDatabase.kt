package com.demo.rssfeedreader

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.rssfeedreader.data.RssFeedChannel
import com.demo.rssfeedreader.data.RssFeedChannelDao
import com.demo.rssfeedreader.data.RssFeedItem
import com.demo.rssfeedreader.data.RssFeedItemDao
import com.demo.rssfeedreader.data.converter.DateConverter

/**
 * Rss feed database
 */
@Database(entities = [RssFeedChannel::class, RssFeedItem::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class RssFeedDatabase : RoomDatabase() {

    abstract fun getRssFeedChannelDao() : RssFeedChannelDao
    abstract fun getRssFeedItemDao() : RssFeedItemDao

    companion object {

        /**
         * Singleton desing pattern
         */
        @Volatile private var instance: RssFeedDatabase? = null

        /**
         * Get single database instance
         */
        fun getInstance(context: Context) : RssFeedDatabase {
            instance?.let { return it }
            synchronized(this) {
                // Build a new instance of database
                return Room.databaseBuilder(context, RssFeedDatabase::class.java, DATABASE_NAME).build().also {
                    instance = it
                }
            }
        }

    }

}