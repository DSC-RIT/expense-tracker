package com.example.budgetmanager.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.database.budget.BudgetDao
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.database.transaction.TransactionDao

class DataBaseRepository(private val transactionDao: TransactionDao, private val budgetDao: BudgetDao) {
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    val allTransactions = transactionDao.getAllTransactions()
    val budget = budgetDao.getAllBudgets()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun clear() {
        transactionDao.clear()
    }

    suspend fun insertBudget(budget: Budget){
        budgetDao.addBudget(budget)
    }

    suspend fun deleteBudget(budget: Budget){
        budgetDao.deleteBudget(budget)
    }
    suspend fun updateBudget(budget: Budget){
        budgetDao.updateBudget(budget)
    }
}