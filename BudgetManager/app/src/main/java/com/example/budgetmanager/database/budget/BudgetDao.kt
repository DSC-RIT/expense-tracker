package com.example.budgetmanager.database.budget

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgetsTable")
    fun getAllBudgets(): LiveData<List<Budget>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBudget(budget: Budget)

    @Update
    suspend fun updateBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)
}