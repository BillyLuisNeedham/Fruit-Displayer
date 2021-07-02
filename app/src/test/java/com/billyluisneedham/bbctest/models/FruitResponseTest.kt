package com.billyluisneedham.bbctest.models

import com.billyluisneedham.bbctest.mocks.MockFruit
import com.billyluisneedham.bbctest.utils.toModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class FruitResponseTest {

    private val fruitResponse = MockFruit.mockFruitResponse
    private lateinit var fruit: Fruit

    @Before
    fun setUp() {
        fruit = fruitResponse.toModel()
    }


    @Test
    fun toModelExtensionFunctionReturnsAFruitModelWithTypePropertyOfFruitResponse() {
        assertThat(fruit.type, `is`(fruitResponse.type))
    }

    @Test
    fun toModelExtensionFunctionReturnsAFruitModelWithPricePropertyOfFruitResponse() {
        assertThat(fruit.price, `is`(fruitResponse.price))
    }

    @Test
    fun toModelExtensionFunctionReturnsAFruitModelWithWeightPropertyOfFruitResponse() {
        assertThat(fruit.weight, `is`(fruitResponse.weight))
    }

    @Test
    fun toModelExtensionFunctionReturnsAFruitModelWithADefaultIdOfZero() {
        assertThat(fruit.fruitId, `is`(0))
    }
}