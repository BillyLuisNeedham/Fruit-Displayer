package com.billyluisneedham.bbctest.utils

import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.models.FruitResponse
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