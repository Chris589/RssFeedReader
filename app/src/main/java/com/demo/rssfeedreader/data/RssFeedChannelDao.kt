package com.demo.rssfeedreader.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Rss feed channel DAO
 */
@Dao
interface RssFeedChannelDao {

    @Query("SELECT * FROM rssfeedchannels ORDER BY pubDate DESC")
    fun getAll() : LiveData<List<RssFeedChannel>>

    @Query("SELECT * FROM rssfeedchannels WHERE title = :title")
    fun get(title: String) : LiveData<RssFeedChannel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: RssFeedChannel) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feeds: List<RssFeedChannel>)

}