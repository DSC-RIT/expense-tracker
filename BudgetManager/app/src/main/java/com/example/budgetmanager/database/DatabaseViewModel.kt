package com.example.budgetmanager.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.database.budget.BudgetDatabase
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.database.transaction.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application): AndroidViewModel(application) {
    public var allNote: LiveData<List<Transaction>>
    var budget: Budget
    public var repository: DataBaseRepository
    init {
        val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        val budgetDao = BudgetDatabase.getDatabase(application).budgetDao()
        repository = DataBaseRepository(transactionDao, budgetDao)
        allNote = repository.allTransactions
        budget = repository.getBudget
    }
    fun addTransaction(vararg transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(*transaction)
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