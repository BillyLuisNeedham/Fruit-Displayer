package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
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
    fun fruitListLoadsOnInit_flowOfFruitListReturnedByFruitRepository_fruitListFromRepositoryLoadedOnInitOfViewModel() {
        runBlocking {
            val fruit = MockFruit.mockFruit
            val flow = flow {
                emit(listOf(fruit))
            }

            coEvery {
                mockFruitRepository.getFruits()
            } returns flow

            fruitListViewModel = FruitListViewModel(mockFruitRepository)

            fruitListViewModel.fruitList.observeForever {}

            assertThat(fruitListViewModel.fruitList.value, `is`(listOf(fruit)))
        }
    }

}