package com.demo.rssfeedreader.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Room entity - RSS feed item (article)
 */
@Entity(tableName = "rssfeeditems")
data class RssFeedItem (
    @PrimaryKey var title: String = "",
    var description: String = "",
    var link: String = "",
    var pubDate: Date = Date(),
    var imageLink: String = ""
) {
    override fun toString(): String  = title
}