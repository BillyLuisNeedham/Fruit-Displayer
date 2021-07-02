package com.billyluisneedham.bbctest.utils

import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.models.FruitResponse

fun FruitResponse.toModel() = Fruit(
    type = this.type,
    weight = this.weight,
    price = this.price
)