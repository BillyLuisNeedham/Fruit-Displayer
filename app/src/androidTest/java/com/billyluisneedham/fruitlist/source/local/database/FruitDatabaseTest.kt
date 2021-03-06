package com.billyluisneedham.fruitlist.source.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.billyluisneedham.fruitlist.mocks.MockFruit
import kotlinx.coroutines.flow.first
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

        fruitDao.saveFruits(fruit)

        val flow = fruitDao.getAllFruits()
        val result = flow.first()
        assertThat(result[0], `is`(expectedFruitWithIncrementedId))
    }

    @Test
    @Throws(Exception::class)
    fun deleteFruitFromDb() = runBlocking {
        val fruit = listOf(MockFruit.mockFruit)

        //confirm table is empty
        val initialTableSize: Int = fruitDao.getAllFruits().first().size
        assertThat(initialTableSize, `is`(0))

        //add to table and confirm it adds row
        fruitDao.saveFruits(fruit)
        val tableSizePostInsert: Int = fruitDao.getAllFruits().first().size
        assertThat(tableSizePostInsert, `is`(1))

        //delete table and confirm the size returns to empty
        fruitDao.deleteAllFruits()
        val tableSizePostDelete: Int = fruitDao.getAllFruits().first().size
        assertThat(tableSizePostDelete, `is`(0))


    }


}