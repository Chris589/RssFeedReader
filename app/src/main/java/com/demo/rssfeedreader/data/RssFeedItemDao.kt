package com.demo.rssfeedreader.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Rss feed item DAO
 */
@Dao
interface RssFeedItemDao {

    @Query("SELECT * FROM rssfeeditems ORDER BY pubDate DESC")
    fun getAll() : LiveData<List<RssFeedItem>>

    @Query("SELECT * FROM rssfeeditems WHERE title = :itemTitle")
    fun get(itemTitle: String) : LiveData<RssFeedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: RssFeedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<RssFeedItem>)

}