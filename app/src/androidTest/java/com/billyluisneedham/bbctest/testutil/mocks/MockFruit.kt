package com.billyluisneedham.bbctest.testutil.mocks

import com.billyluisneedham.bbctest.models.Fruit

class MockFruit {

    companion object {
        const val MOCK_TYPE = "apple"
        const val MOCK_WEIGHT = 800
        const val MOCK_PRICE = 120

        val mockFruit = Fruit(
            type = MOCK_TYPE,
            weight = MOCK_WEIGHT,
            price = MOCK_PRICE
        )

    }

}
