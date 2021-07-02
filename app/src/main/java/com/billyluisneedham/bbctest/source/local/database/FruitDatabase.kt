package com.billyluisneedham.bbctest.source.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class FruitDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: FruitDatabase? = null
        private const val DATABASE_NAME = "fruit-db"

        fun getDatabase(context: Context): FruitDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                FruitDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}