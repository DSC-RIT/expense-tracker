package com.example.budgetmanager.database.budget

import androidx.room.*

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets")
    fun getBudget(): Budget

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBudget(budget: Budget)

    @Update
    suspend fun updateBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)
}