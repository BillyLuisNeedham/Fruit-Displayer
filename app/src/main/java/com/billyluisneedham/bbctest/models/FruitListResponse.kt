package com.billyluisneedham.bbctest.models

import com.google.gson.annotations.SerializedName


data class FruitListResponse(
    @SerializedName("fruit") val fruits: List<FruitResponse>
    )