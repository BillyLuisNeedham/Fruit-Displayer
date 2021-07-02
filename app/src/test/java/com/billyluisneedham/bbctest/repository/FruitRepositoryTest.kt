package com.billyluisneedham.bbctest.repository

import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.remote.service.Service
import com.billyluisneedham.bbctest.testutil.mocks.MockFruit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FruitRepositoryTest {

    private val mockService = mockk<Service>()
    private lateinit var fruitRepository: FruitRepository

    @Test
    fun getFruitCallsCorrectMethodInService() {
        runBlocking {

            coEvery {
                mockService.getFruits()
            } returns MockFruit.mockFruitListResponse

            fruitRepository = FruitRepository(mockService)

            fruitRepository.getFruits()

            coVerify(exactly = 1) {
                mockService.getFruits()
            }

        }
    }

    @Test
    fun getFruitCallsReturnsAListOfFruitResponseFromService() {
        runBlocking {

            coEvery {
                mockService.getFruits()
            } returns MockFruit.mockFruitListResponse

            fruitRepository = FruitRepository(mockService)

            val response = fruitRepository.getFruits()

            assertThat(response, `is`(MockFruit.listOfMockFruitResponse))
        }
    }
}