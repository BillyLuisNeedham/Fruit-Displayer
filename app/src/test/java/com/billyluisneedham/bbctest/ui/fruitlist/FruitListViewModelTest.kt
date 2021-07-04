package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class FruitListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val mockFruitRepository = mockk<FruitRepository>()
    private val fruitListResource = Resource.success(listOf(MockFruit.mockFruit))



    @Test
    fun fruitListLoadsOnInit_liveDataOfFruitListWrappedInResultReturnedByFruitRepository_fruitListFromRepositoryLoadedOnInitOfViewModel() {
        runBlocking {
            val liveDataFruit = MutableLiveData(fruitListResource)

            coEvery {
                mockFruitRepository.getFruits()
            } returns liveDataFruit

            val fruitListViewModel = FruitListViewModel(mockFruitRepository)


            fruitListViewModel.fruitList.observeForever {}


            assertThat(fruitListViewModel.fruitList.value, `is`(fruitListResource))
        }
    }

    @Test
    fun refresh_resultsReturnedOnInitialGetFruitAndSecondGetFruitCallDifferent_differentResultsAreReturnedToViewModel() {
        val testType = "test"
        val secondFruit = MockFruit.mockFruit.copy(fruitId = 3, type=testType)
        val secondFruitListResponse = Resource.success(listOf(secondFruit))
        val liveDataFruit = MutableLiveData(fruitListResource)
        val secondLiveDataFruit = MutableLiveData(secondFruitListResponse)

        coEvery {
            mockFruitRepository.getFruits()
        } returns liveDataFruit andThen secondLiveDataFruit

        val fruitListViewModel = FruitListViewModel(mockFruitRepository)

        fruitListViewModel.fruitList.observeForever {}

        assertThat(fruitListViewModel.fruitList.value, `is`(fruitListResource))

        fruitListViewModel.refreshFruits()

        assertThat(fruitListViewModel.fruitList.value, `is`(secondFruitListResponse))

    }

}