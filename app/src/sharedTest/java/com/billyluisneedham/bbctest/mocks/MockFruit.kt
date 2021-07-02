package com.billyluisneedham.bbctest.mocks

import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.models.FruitListResponse
import com.billyluisneedham.bbctest.models.FruitResponse

object MockFruit {

    const val MOCK_TYPE = "apple"
    const val MOCK_WEIGHT = 800
    const val MOCK_PRICE = 120

    val mockFruitResponse = FruitResponse(
        type = MOCK_TYPE,
        weight = MOCK_WEIGHT,
        price = MOCK_PRICE
    )

    val mockFruit = Fruit(
        type = MOCK_TYPE,
        weight = MOCK_WEIGHT,
        price = MOCK_PRICE
    )

    private val listOfMockFruitResponse = listOf(mockFruitResponse)

    val mockFruitListResponse = FruitListResponse(fruits = listOfMockFruitResponse)


}