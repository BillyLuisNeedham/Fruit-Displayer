package com.billyluisneedham.bbctest.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fruit(
    @PrimaryKey(autoGenerate = true) val fruitId: Int = 0,
    val type: String,
    val weight: Int,
    val price: Int
)
