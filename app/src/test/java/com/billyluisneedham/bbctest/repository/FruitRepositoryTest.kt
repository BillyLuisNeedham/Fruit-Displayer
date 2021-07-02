package com.billyluisneedham.bbctest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.remote.service.Service
import com.billyluisneedham.bbctest.utils.toModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class FruitRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockService = mockk<Service>()
    private val mockDao = mockk<FruitDao>()
    private lateinit var fruitRepository: FruitRepository
    private val mockFruits = listOf(MockFruit.mockFruit)

    private fun initFruitRepositoryForTest() {
        fruitRepository = FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockService
        )
    }

    private fun initMocksForSuccessfulFetch() {
        val mappedFruitResponse = MockFruit.mockFruitListResponse.fruits.map {
            it.toModel()
        }

        val flow = flow {
            emit(mockFruits)
        }
        coEvery {
            mockService.getFruits()
        } returns MockFruit.mockFruitListResponse

        coEvery {
            mockDao.getAllFruits()
        } returns flow

        coEvery {
            mockDao.saveFruits(mappedFruitResponse)
        } returns Unit
    }

    @Test
    fun getAllFruitInsertsFruitsFromRemoteServiceToLocalDb() {
        runBlocking {
            val mappedFruitResponse = MockFruit.mockFruitListResponse.fruits.map {
                it.toModel()
            }
            initMocksForSuccessfulFetch()
            initFruitRepositoryForTest()


            fruitRepository.getFruits()

            coVerify(exactly = 1) {
                mockDao.saveFruits(mappedFruitResponse)
            }

        }
    }

    @Test
    fun getAllFruitCallsReturnsAFlowOfListOfFruitFromDb() {
        runBlocking {
            initMocksForSuccessfulFetch()
            initFruitRepositoryForTest()

            val response = fruitRepository.getFruits().first()

            assertThat(response, `is`(mockFruits))
        }
    }
}