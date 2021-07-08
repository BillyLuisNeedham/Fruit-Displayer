package com.billyluisneedham.fruitlist.ui.fruitdetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.mocks.MockFruit
import com.billyluisneedham.fruitlist.ui.fruitdetail.FruitDetailFragment.Companion.ARG_PRICE
import com.billyluisneedham.fruitlist.ui.fruitdetail.FruitDetailFragment.Companion.ARG_TYPE
import com.billyluisneedham.fruitlist.ui.fruitdetail.FruitDetailFragment.Companion.ARG_WEIGHT
import com.billyluisneedham.fruitlist.utils.capitalise
import org.junit.Before
import org.junit.Test
import java.text.NumberFormat
import java.util.*

class FruitDetailFragmentTest {
    private val fragmentArgs = bundleOf(
        ARG_TYPE to MockFruit.mockFruit.type,
        ARG_PRICE to MockFruit.mockFruit.price,
        ARG_WEIGHT to MockFruit.mockFruit.weight
    )
    private lateinit var weightMessage: String
    private lateinit var priceMessage: String
    private lateinit var kg: String

    @Before
    fun setUp() {
        weightMessage =
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.weight)
        priceMessage = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.price)
        kg = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.kg)
    }

    @Test
    fun fruitType_typePassedViaArgs_displaysCapitalisedTypeOfFruitPassedByArgs() {
        initFragmentForTests()

        onView(withId(R.id.tvFruitType))
            .check(matches(withText(MockFruit.mockFruit.type.capitalise())))
    }

    private fun initFragmentForTests() {
        launchFragmentInContainer<FruitDetailFragment>(fragmentArgs)

    }

    @Test
    fun fruitWeight_weightPassedViaArgs_displaysTheWeightConvertedFromGramsToKgsWithCorrectMessage() {
        initFragmentForTests()
        val expectedWeight: Double = MockFruit.mockFruit.weight.toDouble() / 1000

        onView(withText("$weightMessage $expectedWeight$kg"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun fruitPrice_pricePassedViaArgs_displaysThePriceConvertedFromPenceToPoundWithCorrectMessageAndZeroAppendedToPriceIfRequired() {
        initFragmentForTests()
        val price: Double = MockFruit.mockFruit.price.toDouble()
        val n = NumberFormat.getCurrencyInstance(Locale.UK)
        val expectedPrice = n.format(price / 100)

        onView(withText("$priceMessage $expectedPrice"))
            .check(matches(isDisplayed()))
    }
}