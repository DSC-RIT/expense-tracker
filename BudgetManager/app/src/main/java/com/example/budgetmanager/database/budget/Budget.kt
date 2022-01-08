package com.example.budgetmanager.database.budget

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    val budget: String,
    val foodBudget: String,
    val groceryBudget: String,
    val stationaryBudget: String,
    val rechargeBudget: String,
    val travellingBudget: String,
    val clothingBudget: String,
    val otherBudget: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {

}