package com.demo.rssfeedreader.data

import androidx.room.*
import java.util.Date

/**
 * Room entity - RSS feed channel
 */
@Entity(tableName = "rssfeedchannels",indices = [
    Index(value = ["title", "link"],
        unique = true
    )])
data class RssFeedChannel(
    var title: String = "",
    var description: String = "",
    var copyright: String = "",
    var link: String = "",
    var pubDate: Date = Date(),
    @Ignore var items: MutableList<RssFeedItem> = mutableListOf()
) {
    @PrimaryKey(autoGenerate = true) var id = 0

    override fun toString(): String  = title
}