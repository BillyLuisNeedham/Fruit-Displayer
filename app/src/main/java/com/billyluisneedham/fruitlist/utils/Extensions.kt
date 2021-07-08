package com.billyluisneedham.fruitlist.utils

import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.models.FruitResponse
import java.util.*

fun FruitResponse.toModel() = Fruit(
    type = this.type,
    weight = this.weight,
    price = this.price
)

fun String.capitalise() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
}