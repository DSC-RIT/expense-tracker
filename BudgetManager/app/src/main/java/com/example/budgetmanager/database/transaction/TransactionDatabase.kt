package com.example.budgetmanager.database.transaction

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    companion object {
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}