package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.billyluisneedham.bbctest.R
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.utils.Resource
import com.billyluisneedham.bbctest.utils.capitalise
import io.mockk.coEvery
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test

class FruitListFragmentTest {

    private val mockFruitRepository = mockk<FruitRepository>()
    private lateinit var errorMessage: String

    @Before
    fun setUp() {
        errorMessage =
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.error_message)
    }


    @Test
    fun displaysTypeOfFruit_fruitListReturnedSuccessfullyByRepository_typeOfFruitIsDisplayedToTheUser() {
        initMockSuccessfulResourceFetchedFromRepo()

        val fruitListFragment = FruitListFragment(mockFruitRepository)
        launchFragmentInContainer {
            fruitListFragment
        }

        onView(withText(MockFruit.mockFruit.type.capitalise()))
            .check(matches(isDisplayed()))
    }

    private fun initMockSuccessfulResourceFetchedFromRepo() {
        val fruitListResponse = Resource.success(listOf(MockFruit.mockFruit))
        val liveDataResponse = MutableLiveData(fruitListResponse)

        coEvery {
            mockFruitRepository.getFruits()
        } returns liveDataResponse

    }

    @Test
    fun displaysLoadingUi_fruitListReturnedFromRepositoryIsResourceLoading_loadingUiVisibleToUser() {

        val response = Resource.loading(listOf<Fruit>())
        val liveDataResponse = MutableLiveData(response)

        coEvery { mockFruitRepository.getFruits() } returns liveDataResponse

        val fruitListFragment = FruitListFragment(mockFruitRepository)
        launchFragmentInContainer { fruitListFragment }

        onView(withId(R.id.loadingUi))
            .check(matches(isDisplayed()))

    }

    @Test
    fun hideLoadingUi_fruitListReturnedFromRepositoryIsLoadingThenSuccess_loadingUiNotVisible() {
        val initialResponse = Resource.loading(listOf<Fruit>())
        val finalResponse = Resource.success(listOf(MockFruit.mockFruit))
        val liveDataResponse = MutableLiveData(initialResponse)

        coEvery { mockFruitRepository.getFruits() } returns liveDataResponse

        val fruitListFragment = FruitListFragment(mockFruitRepository)
        launchFragmentInContainer {
            fruitListFragment
        }

        onView(withId(R.id.loadingUi))
            .check(matches(isDisplayed()))

        liveDataResponse.postValue(finalResponse)

        onView(withId(R.id.loadingUi))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun showErrorMessage_fruitListReturnedFromRepositoryIsError_errorUiIsDisplayedToUserWithPassedMessage() {
        val testErrorMessage = "test error message"
        val errorResponse = Resource.error(
            message = testErrorMessage, data = listOf<Fruit>()
        )
        val liveDataResponse = MutableLiveData(errorResponse)

        coEvery { mockFruitRepository.getFruits() } returns liveDataResponse

        val fruitListFragment = FruitListFragment(mockFruitRepository)
        launchFragmentInContainer {
            fruitListFragment
        }

        onView(withId(R.id.tvErrorMsg))
            .check(matches(isDisplayed()))
            .check(matches(withText("$errorMessage $testErrorMessage")))
    }

    @Test
    fun refreshButton_refreshButtonClickedRepositoryReturnsDifferentDataOnSecondGetFruitCall_latestDataDisplayedInUi() {
        val fruitListResponse = Resource.success(listOf(MockFruit.mockFruit))
        val liveDataResponse = MutableLiveData(fruitListResponse)
        val testType = "Test Type"
        val secondFruit = MockFruit.mockFruit.copy(fruitId = 4, type = testType)
        val secondFruitListResponse = Resource.success(listOf(secondFruit))
        val secondLiveDataResponse = MutableLiveData(secondFruitListResponse)

        coEvery {
            mockFruitRepository.getFruits()
        } returns liveDataResponse andThen secondLiveDataResponse

        launchFragmentInContainer { FruitListFragment(mockFruitRepository) }

        onView(withText(MockFruit.mockFruit.type.capitalise()))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnRefresh))
            .perform(click())

        onView(withText(testType))
            .check(matches(isDisplayed()))


    }
}