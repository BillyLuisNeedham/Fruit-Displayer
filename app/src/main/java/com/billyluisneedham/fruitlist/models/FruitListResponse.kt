package com.billyluisneedham.fruitlist.models

import com.google.gson.annotations.SerializedName


data class FruitListResponse(
    @SerializedName("fruit") val fruits: List<FruitResponse>
    )