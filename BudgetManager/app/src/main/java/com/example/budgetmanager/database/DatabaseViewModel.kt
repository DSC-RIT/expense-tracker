package com.example.budgetmanager.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.database.budget.BudgetDatabase
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.database.transaction.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel: ViewModel() {
    private lateinit var repository: DataBaseRepository
    lateinit var allTransactions: LiveData<List<Transaction>>
    lateinit var budget: LiveData<List<Budget>>

    fun initial(context: Context) {
        val transactionDao = TransactionDatabase.getDatabase(context).transactionDao()
        val budgetDao = BudgetDatabase.getDatabase(context).budgetDao()
        repository = DataBaseRepository(transactionDao, budgetDao)
        allTransactions = repository.allTransactions
        budget = repository.budget
    }

    fun addTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(transaction)
    }

    fun clear() = viewModelScope.launch(Dispatchers.IO) {
        repository.clear()
    }

    fun addBudget(budget: Budget) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBudget(budget)
    }

    fun updateBudget(budget: Budget) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateBudget(budget)
    }

    fun deleteBudget(budget: Budget) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBudget(budget)
    }
}