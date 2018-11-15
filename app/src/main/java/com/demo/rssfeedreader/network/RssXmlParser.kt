package com.demo.rssfeedreader.network

import android.util.Xml
import com.demo.rssfeedreader.data.RssFeedChannel
import com.demo.rssfeedreader.data.RssFeedItem
import com.demo.rssfeedreader.utils.DateParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.util.*

/**
 * Rss feed xml parser
 */
class RssXmlParser {

    // Xml namespace (not used here)
    private val ns: String? = null

    /**
     * Parse the rss feed stream
     * @param inputStream rss feed stream
     * @return a list of rss channels
     */
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<RssFeedChannel> {
        inputStream.use { it ->
            val channels = mutableListOf<RssFeedChannel>()
            val parser: XmlPullParser = Xml.newPullParser()

            // Configure Xml parser
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()

            // Parse first rss tag
            parser.require(XmlPullParser.START_TAG, ns, "rss")
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                if (parser.name == "channel") {
                    // Parse inner tag -> channel
                    channels.add(readChannel(parser))
                } else {
                    // Ignore other tags
                    skipElement(parser)
                }
            }

            return channels
        }
    }

    /**
     * Read Rss Channel
     * @param parser xml parser
     * @return rss feed channel
     */
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readChannel(parser: XmlPullParser): RssFeedChannel {
        parser.require(XmlPullParser.START_TAG, ns, "channel")
        val channel = RssFeedChannel()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "item") {
                // Parse inner tag -> item
                channel.items.add(readFeedItem(parser))
            } else {
                // Parse channel tags
                when (parser.name) {
                    "title" -> channel.title = readTag(parser, "title")
                    "description" -> channel.description = readTag(parser, "description")
                    "link" -> channel.link = readTag(parser, "link")
                    "copyright" -> channel.copyright = readTag(parser, "copyright")
                    "pubDate" -> channel.pubDate = DateParser.stringToDate(readTag(parser, "pubDate"), "EEE, d MMM yyyy HH:mm:ss Z") ?: Date()
                    else -> skipElement(parser)
                }
            }
        }
        return channel
    }

    /**
     * Read feed item
     * @param parser xml parser
     * @return rss feed item
     */
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeedItem(parser: XmlPullParser): RssFeedItem {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        val item = RssFeedItem()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Parse citem tags
            when (parser.name) {
                "title" -> item.title = readTag(parser, "title")
                "description" -> item.description = readTag(parser, "description")
                "link" -> item.link = readTag(parser, "link")
                "enclosure" -> item.imageLink = readAttributeFromTag(parser, "enclosure", "url")
                "pubDate" -> item.pubDate = DateParser.stringToDate(readTag(parser, "pubDate"), "EEE, d MMM yyyy HH:mm:ss Z") ?: Date()
                else -> skipElement(parser)
            }
        }
        return item
    }

    /**
     * Read xml tag
     * @param parser xml parser
     * @param tagName tag to read
     * @return tag content
     */
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTag(parser: XmlPullParser, tagName: String) : String {
        parser.require(XmlPullParser.START_TAG, ns, tagName)
        val text = readTextElement(parser)
        parser.require(XmlPullParser.END_TAG, ns, tagName)
        return text
    }

    /**
     * Read xml tag
     * @param parser xml parser
     * @param tagName tag to read
     * @param attributeName attribute to read
     * @return attribute content
     */
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttributeFromTag(parser: XmlPullParser, tagName: String, attributeName: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tagName)
        val attribute = parser.getAttributeValue(null, attributeName)
        parser.nextTag()
        parser.require(XmlPullParser.END_TAG, ns, tagName)
        return attribute
    }

    /**
     * Read text element
     * @param parser xml parser
     * @return text
     */
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTextElement(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    /**
     * Skip an xml tag to ignore it
     * @param parser xml parser
     */
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skipElement(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}