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
    private lateinit var fruitListViewModel: FruitListViewModel


    @Test
    fun fruitListLoadsOnInit_liveDataOfFruitListWrappedInResultReturnedByFruitRepository_fruitListFromRepositoryLoadedOnInitOfViewModel() {
        runBlocking {
            val fruitListResource = Resource.success(listOf(MockFruit.mockFruit))
            val liveDataFruit = MutableLiveData(fruitListResource)

            coEvery {
                mockFruitRepository.getFruits()
            } returns liveDataFruit

            fruitListViewModel = FruitListViewModel(mockFruitRepository)


            fruitListViewModel.fruitList.observeForever {}


            assertThat(fruitListViewModel.fruitList.value, `is`(fruitListResource))
        }
    }

}