package com.billyluisneedham.bbctest.source.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.billyluisneedham.bbctest.testutil.mocks.MockFruit
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

class FruitDatabaseTest {

    private lateinit var db: FruitDatabase
    private lateinit var fruitDao: FruitDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FruitDatabase::class.java
        ).build()
        fruitDao = db.getFruitDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeFruitAndReadInList() = runBlocking {
        val fruit = listOf(MockFruit.mockFruit)
        val expectedFruitWithIncrementedId = MockFruit.mockFruit.copy(fruitId = 1)

        fruitDao.insertAll(fruit)

        val result = fruitDao.getAll()
        assertThat(result[0], `is`(expectedFruitWithIncrementedId))
    }


}