package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.databinding.FourthFragmentBinding

class HistoryFragment: Fragment() {
    private lateinit var binding: FourthFragmentBinding
    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseViewModel.initial(requireNotNull(this.activity).application)
        binding = DataBindingUtil.inflate(inflater, R.layout.fourth_fragment, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        databaseViewModel.allTransactions.observe(viewLifecycleOwner) {
            val recyclerView = binding.history
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = HistoryAdapter(it)
        }
    }
}