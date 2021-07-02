package com.billyluisneedham.bbctest.repository

import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.remote.service.Service
import com.billyluisneedham.bbctest.mocks.MockFruit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FruitRepositoryTest {

    private val mockService = mockk<Service>()
    private val mockDao = mockk<FruitDao>()
    private lateinit var fruitRepository: FruitRepository

    private fun setUpFruitRepositoryForTest() {
        fruitRepository = FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockService
        )
    }

    @Test
    fun getFruitCallsCorrectMethodInService() {
        runBlocking {

            coEvery {
                mockService.getFruits()
            } returns MockFruit.mockFruitListResponse

            setUpFruitRepositoryForTest()

            fruitRepository.getFruits()

            coVerify(exactly = 1) {
                mockService.getFruits()
            }

        }
    }

    @Test
    fun getFruitCallsReturnsAFlowOfListOfFruitFromDb() {
        runBlocking {

            coEvery {
                mockDao.getFruits()
            } returns MockFruit.mockFruitListResponse

            setUpFruitRepositoryForTest()

            val response = fruitRepository.getFruits()

            assertThat(response, `is`(MockFruit.listOfMockFruitResponse))
        }
    }
}