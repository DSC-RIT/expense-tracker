package com.example.budgetmanager.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    public var allNote: LiveData<List<Transaction>>
    public var repository: TransactionRepository
    init {
        val dao = TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        allNote = repository.allTransactions
    }

    fun addTransaction(vararg transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(*transaction)
    }
}