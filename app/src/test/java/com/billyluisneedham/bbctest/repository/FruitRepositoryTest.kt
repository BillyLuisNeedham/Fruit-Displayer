package com.billyluisneedham.bbctest.repository

import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.source.remote.service.DiagnosticEvents
import com.billyluisneedham.bbctest.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.bbctest.utils.Resource
import com.billyluisneedham.bbctest.utils.toModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FruitRepositoryTest {

    private val mockRemoteDataSource = mockk<RemoteFruitDataSource>()
    private val mockDao = mockk<FruitDao>()
    private val mockDiagnosticManager = mockk<SendDiagnosticManager>()
    private val mockFruits = listOf(MockFruit.mockFruit)

    @ExperimentalCoroutinesApi
    @Test
    fun getAllFruitCallsReturnsLiveDataOfResourceListOfFruitFromDb_returnedSuccessfully_ResourceSuccessfulWithCorrectData() {
        runBlocking {
            initMocksForSuccessfulFetch()
            val fruitRepository = initFruitRepositoryForTest()
            val result = fruitRepository.getFruits()



            val expectedResult = Resource.success(mockFruits)
            assertThat(result.value, `is`(expectedResult))
        }
    }

    @ExperimentalCoroutinesApi
    private fun initFruitRepositoryForTest(): FruitRepository {
        return FruitRepository(
            localFruitDataSource = mockDao,
            remoteFruitDataSource = mockRemoteDataSource,
            sendDiagnosticManager = mockDiagnosticManager,
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

}