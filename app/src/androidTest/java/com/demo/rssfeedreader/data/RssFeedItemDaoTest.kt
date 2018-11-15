package com.demo.rssfeedreader.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.demo.rssfeedreader.RssFeedDatabase
import com.demo.rssfeedreader.utilities.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class RssFeedItemDaoTest {

    private lateinit var database: RssFeedDatabase
    private lateinit var dao: RssFeedItemDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sampleOne = RssFeedItem(
        "Sample 1",
        "Description 1",
        "http://www.test.com/1",
        Date(),
        "http://www.test.com/1/img.jpg")

    private val sampleTwo = RssFeedItem(
        "Sample 2",
        "Description 2",
        "http://www.test.com/2",
        Date(),
        "http://www.test.com/2/img.jpg")

    private val sampleThree = RssFeedItem(
        "Sample 3",
        "Description 3",
        "http://www.test.com/3",
        Date(),
        "http://www.test.com/3/img.jpg")

    @Before
    fun initDatabase() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, RssFeedDatabase::class.java).build()

        dao = database.getRssFeedItemDao()

        dao.insert(listOf(sampleOne, sampleTwo, sampleThree))
    }

    @After
    fun destroyDatabase() {
        database.close()
    }

    @Test
    fun getRssFeedItem() {
        val item = getValue(dao.get("Sample 2"))
        assertThat(item, equalTo(sampleTwo))
    }

    @Test
    fun getRssFeedItems() {
        val items = getValue(dao.getAll())
        assertThat(items.size, equalTo(3))

        assertThat(items[0], equalTo(sampleOne))
        assertThat(items[1], equalTo(sampleTwo))
        assertThat(items[2], equalTo(sampleThree))
    }

}