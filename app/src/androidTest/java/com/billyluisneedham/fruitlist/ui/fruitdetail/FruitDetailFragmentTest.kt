package com.billyluisneedham.fruitlist.ui.fruitdetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.mocks.MockFruit
import com.billyluisneedham.fruitlist.utils.capitalise
import org.junit.Before
import org.junit.Test
import java.text.NumberFormat
import java.util.*

class FruitDetailFragmentTest {
    private val fragmentArgs = bundleOf(
        "type" to MockFruit.mockFruit.type,
        "price" to MockFruit.mockFruit.price,
        "weight" to MockFruit.mockFruit.weight
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

        onView(withText(MockFruit.mockFruit.type.capitalise()))
            .check(matches(isDisplayed()))
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