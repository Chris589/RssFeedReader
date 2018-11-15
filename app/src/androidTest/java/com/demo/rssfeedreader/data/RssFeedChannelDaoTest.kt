package com.demo.rssfeedreader.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.demo.rssfeedreader.RssFeedDatabase
import com.demo.rssfeedreader.utilities.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class RssFeedChannelDaoTest {

    private lateinit var database: RssFeedDatabase
    private lateinit var dao: RssFeedChannelDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sampleOne = RssFeedChannel(
        "Sample 1",
        "Description 1",
        "Copyright 1",
        "http://www.test.com/1",
        Date()
    )

    private val sampleTwo = RssFeedChannel(
        "Sample 2",
        "Description 2",
        "Copyright 2",
        "http://www.test.com/2",
        Date()
    )

    private val sampleThree = RssFeedChannel(
        "Sample 3",
        "Description 3",
        "Copyright 3",
        "http://www.test.com/3",
        Date()
    )

    @Before
    fun initDatabase() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, RssFeedDatabase::class.java).build()

        dao = database.getRssFeedChannelDao()

        dao.insert(listOf(sampleOne, sampleTwo, sampleThree))
    }

    @After
    fun destroyDatabase() {
        database.close()
    }

    @Test
    fun getRssFeedChannel() {
        val channel = getValue(dao.get("Sample 2"))
        assertThat(channel, equalTo(sampleTwo))
    }

    @Test
    fun getRssFeedChannels() {
        val channels = getValue(dao.getAll())
        assertThat(channels.size, equalTo(3))
    }

}