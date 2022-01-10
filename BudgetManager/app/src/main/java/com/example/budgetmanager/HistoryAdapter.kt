package com.example.budgetmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.database.transaction.Transaction

class HistoryAdapter(private val pastTransactions: List<Transaction>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)

        return HistoryViewHolder(layout)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val pastTransaction = pastTransactions[position]
        holder.categoryName.text = pastTransaction.category
        holder.amount.text = pastTransaction.amount
        holder.date.text = pastTransaction.date
        holder.time.text = pastTransaction.time
        holder.comment.text = pastTransaction.comment
    }

    override fun getItemCount(): Int = pastTransactions.size

    class HistoryViewHolder(layout: View): RecyclerView.ViewHolder(layout) {
        val categoryName = layout.findViewById<TextView>(R.id.categ_name)
        val date = layout.findViewById<TextView>(R.id.date_val)
        val time = layout.findViewById<TextView>(R.id.time_val)
        val amount = layout.findViewById<TextView>(R.id.amount_val)
        val comment = layout.findViewById<TextView>(R.id.comment)
    }
}