package com.billyluisneedham.bbctest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.utils.Resource
import com.billyluisneedham.bbctest.utils.toModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class FruitRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockRemoteDataSource = mockk<RemoteFruitDataSource>()
    private val mockDao = mockk<FruitDao>()
    private lateinit var fruitRepository: FruitRepository
    private val mockFruits = listOf(MockFruit.mockFruit)

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
    fun getAllFruitCallsReturnsLiveDataOfListOfFruitFromDb_returnedSuccessfully_() {
        runBlocking {
            initMocksForSuccessfulFetch()
            initFruitRepositoryForTest()

            val response = fruitRepository.getFruits().value

            assertThat(response, `is`(mockFruits))
        }
    }

    private fun initFruitRepositoryForTest() {
        fruitRepository = FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockRemoteDataSource
        )
    }

    private fun initMocksForSuccessfulFetch() {
        initMockSuccessfulRemoteDataSourceFetch()
        initMockSuccessfulLocalDataSourceFetch()
        initMockSuccessfulSaveOfFruitInLocalDataSource()
    }

    private fun initMockSuccessfulSaveOfFruitInLocalDataSource() {
        val mappedFruitResponse = MockFruit.mockFruitListResponse.fruits.map {
            it.toModel()
        }

        coEvery {
            mockDao.saveFruits(mappedFruitResponse)
        } returns Unit
    }

    private fun initMockSuccessfulLocalDataSourceFetch() {
        val mockFruitLiveData = MutableLiveData(mockFruits)

        coEvery {
            mockDao.getAllFruits()
        } returns mockFruitLiveData
    }

    private fun initMockSuccessfulRemoteDataSourceFetch() {

        coEvery {
            mockRemoteDataSource.getFruits()
        } returns Resource.success(MockFruit.mockFruitListResponse)

    }

}