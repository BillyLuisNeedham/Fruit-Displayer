package com.billyluisneedham.bbctest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.source.remote.service.DiagnosticEvents
import com.billyluisneedham.bbctest.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.bbctest.testutils.CoroutineTestRule
import com.billyluisneedham.bbctest.utils.Resource
import com.billyluisneedham.bbctest.utils.toModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRemoteDataSource = mockk<RemoteFruitDataSource>()
    private val mockDao = mockk<FruitDao>()
    private val mockDiagnosticManager = mockk<SendDiagnosticManager>()
    private val mockFruits = listOf(MockFruit.mockFruit)

    @After
    fun cleanUp() {
        coroutinesTestRule.testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAllFruitCallsReturnsLiveDataOfResourceListOfFruitFromDb_returnedSuccessfully_ResourceSuccessfulWithCorrectData() {
        runBlockingTest {
            val job = launch {

                initMocksForSuccessfulFetch()
                val fruitRepository = initFruitRepositoryForTest()
                val result = fruitRepository.getFruits().asFlow().last()

                val expectedResult = Resource.success(mockFruits)
                assertThat(result, `is`(expectedResult))
            }

            job.cancel()
        }
    }

    private fun initFruitRepositoryForTest(): FruitRepository {
        return FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockRemoteDataSource,
            sendDiagnosticManager = mockDiagnosticManager,
            dispatcher = coroutinesTestRule.testDispatcher
        )
    }

    private fun initMocksForSuccessfulFetch() {
        initMockSuccessfulRemoteDataSourceFetch()
        initMockSuccessfulLocalDataSourceFetch()
        initMockSuccessfulSaveOfFruitInLocalDataSource()
        initMockSuccessfulDeleteOfDb()
        initMockForSendDiagnostics()
    }

    private fun initMockForSendDiagnostics() {
        coEvery {
            mockDiagnosticManager.sendDiagnostics(event = DiagnosticEvents.Load, data = any())
        } returns Unit
    }

    private fun initMockSuccessfulDeleteOfDb() {
        coEvery {
            mockDao.deleteAllFruits()
        } returns Unit
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
        val flow = flow {
            emit(mockFruits)
        }

        coEvery {
            mockDao.getAllFruits()
        } returns flow
    }

    private fun initMockSuccessfulRemoteDataSourceFetch() {
        coEvery {
            mockRemoteDataSource.getFruits()
        } returns Resource.success(MockFruit.mockFruitListResponse)
    }

    @Test
    fun remoteService_getAllFruitInsertsFruitsFromRemoteServiceToLocalDb_listFromRemoteServiceSavedInDb() {
        runBlockingTest {
            val job = launch {

                val mappedFruitResponse = MockFruit.mockFruitListResponse.fruits.map {
                    it.toModel()
                }
                initMocksForSuccessfulFetch()
                val fruitRepository = initFruitRepositoryForTest()


                fruitRepository.getFruits().asFlow().last()

                coVerify(exactly = 1) {
                    mockDao.saveFruits(mappedFruitResponse)
                }
            }
            job.cancel()

        }
    }

}