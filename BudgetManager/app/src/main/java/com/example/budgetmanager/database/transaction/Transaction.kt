package com.example.budgetmanager.database.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    val budget: String,
    val category: String,
    val comment: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)