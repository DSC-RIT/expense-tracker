package com.example.budgetmanager.database.budget

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class BudgetDatabase: RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
    companion object {
        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getDatabase(context: Context): BudgetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}