package com.example.budgetmanager.database.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionsTable")
data class Transaction(
    val amount: String,
    val category: String,
    val comment: String,
    val date: String,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)