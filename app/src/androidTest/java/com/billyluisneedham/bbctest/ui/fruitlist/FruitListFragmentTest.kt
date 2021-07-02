package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.source.FruitRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class FruitListFragmentTest {

    private val mockFruitRepository = mockk<FruitRepository>()
    private lateinit var fruitListFragment: FruitListFragment

    @Before
    fun setUp() {
    }

    @Test
    fun displaysTypeOfFruit_fruitListReturnedByRepository_typeOfFruitIsDisplayedToTheUser() {

        val fruit = MockFruit.mockFruit
        val flow = flow {
            emit(listOf(fruit))
        }

        coEvery {
            mockFruitRepository.getFruits()
        } returns flow

        fruitListFragment = FruitListFragment(mockFruitRepository)
        launchFragmentInContainer {
            fruitListFragment
        }

        onView(withText(fruit.type))
            .check(matches(isDisplayed()))
    }
}