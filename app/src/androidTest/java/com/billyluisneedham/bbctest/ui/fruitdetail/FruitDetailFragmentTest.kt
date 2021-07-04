package com.billyluisneedham.bbctest.ui.fruitdetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.ui.fruitdetail.FruitDetailFragment.Companion.ARG_PRICE
import com.billyluisneedham.bbctest.ui.fruitdetail.FruitDetailFragment.Companion.ARG_TYPE
import com.billyluisneedham.bbctest.ui.fruitdetail.FruitDetailFragment.Companion.ARG_WEIGHT
import com.billyluisneedham.bbctest.utils.capitalise
import org.junit.Test

class FruitDetailFragmentTest {


    @Test
    fun displaysCapitalisedTypeOfFruitPassedByArgs() {
        val fragmentArgs = bundleOf(
            ARG_TYPE to MockFruit.mockFruit.type,
            ARG_PRICE to MockFruit.mockFruit.price,
            ARG_WEIGHT to MockFruit.mockFruit.weight
        )
        launchFragmentInContainer<FruitDetailFragment>(fragmentArgs)

        onView(withText(MockFruit.mockFruit.type.capitalise()))
            .check(matches(isDisplayed()))
    }
}