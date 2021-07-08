package com.billyluisneedham.fruitlist.mocks

import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.models.FruitListResponse
import com.billyluisneedham.fruitlist.models.FruitResponse

object MockFruit {

    private const val MOCK_TYPE = "apple"
    private const val MOCK_WEIGHT = 800
    private const val MOCK_PRICE = 120

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