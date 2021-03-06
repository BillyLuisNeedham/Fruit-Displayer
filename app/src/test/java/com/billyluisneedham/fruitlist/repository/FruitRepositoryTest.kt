package com.billyluisneedham.fruitlist.repository

import androidx.lifecycle.asFlow
import com.billyluisneedham.fruitlist.mocks.MockFruit
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.source.local.database.FruitDao
import com.billyluisneedham.fruitlist.source.remote.RemoteFruitDataSource
import com.billyluisneedham.fruitlist.source.remote.service.DiagnosticEvents
import com.billyluisneedham.fruitlist.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.fruitlist.testutils.CoroutineTestRule
import com.billyluisneedham.fruitlist.utils.Resource
import com.billyluisneedham.fruitlist.utils.toModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRemoteDataSource = mockk<RemoteFruitDataSource>()
    private val mockDao = mockk<FruitDao>()
    private val mockDiagnosticManager = mockk<SendDiagnosticManager>()
    private val mockFruits = listOf(MockFruit.mockFruit)


    @Test
    fun getAllFruitCallsReturnsLiveDataOfResourceListOfFruitFromDb_returnedSuccessfully_ResourceSuccessfulWithCorrectData() {
        runBlocking {
//        coroutinesTestRule.testDispatcher.runBlockingTest {
//            val job = launch {
            initMocksForSuccessfulFetch()
            val fruitRepository = initFruitRepositoryForTest()
            val result = fruitRepository.getFruits()
            result.observeForever {}
            val expectedResult = Resource.success(mockFruits)
            assertThat(result.value, `is`(expectedResult))


        }
//        }

//            job.cancel()
//    }
    }

    private fun initFruitRepositoryForTest(): FruitRepository {
        return FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockRemoteDataSource,
            sendDiagnosticManager = mockDiagnosticManager,
            dispatcher = Dispatchers.Main
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

    @Test
    fun networkCallToSaveTimeMeasurement_mockNetworkCallTakesSetAmountOfTime_diagnosticsSentWithCorrectTime() {
        runBlockingTest {
            val job = launch {


            }

            job.cancel()
        }
    }
}

