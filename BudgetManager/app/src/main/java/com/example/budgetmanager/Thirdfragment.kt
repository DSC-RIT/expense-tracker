package com.example.budgetmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.databinding.ThirdFragmentBinding

class Thirdfragment : Fragment() {
    // I have initialized the databaseViewModel here
    private val databaseViewModel: DatabaseViewModel by viewModels()

    private lateinit var binding: ThirdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // This is important to access the database, leave the code as it is
        databaseViewModel.initial(requireNotNull(this.activity).application)

        binding = DataBindingUtil.inflate(inflater,R.layout.third_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = binding.doneAdd
        btn.setOnClickListener{
//            The below code is an example for how to insert into the database:
//
//            val entry = Transaction("123", "Food", "Test", "08/01/22", "20:55:30")
//            databaseViewModel.addTransaction(entry)

            method()
        }
    }

    private fun method()
    {
        val act = ThirdfragmentDirections.actionThirdfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
