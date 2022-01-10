package com.example.budgetmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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
import kotlinx.android.synthetic.main.third_fragment.*
import java.text.SimpleDateFormat
import java.util.*

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
        var rb : RadioButton? = null

        binding.rdGroup.setOnCheckedChangeListener {_, i ->
            rb = getView()?.findViewById(i)
        }

        btn.setOnClickListener{
//            The below code is an example for how to insert into the database:
//
//            val entry = Transaction("123", "Food", "Test", "08/01/22", "20:55:30")
//            databaseViewModel.addTransaction(entry)

            val amountSpent = binding.textAmountSpentEditText.text.toString()

            if (amountSpent.isEmpty()){
                Toast.makeText(activity, "Please enter the amount!", Toast.LENGTH_SHORT).show()
            }
            else {
                val amount = amountSpent.toIntOrNull()

                if (amount == null || amount <= 0) {
                    Toast.makeText(activity, "Please enter a valid amount!", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (rb == null) {
                        Toast.makeText(activity, "Please Select the category!", Toast.LENGTH_SHORT).show()
                    } else {
                        val selectedCategory = rb!!.text.toString()
                        val comment = TransactionComment.text.toString()

                        val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                        val dateTimeArray = sdf.format(Date()).toString().split(" ")

                        val newTransaction = Transaction(
                            amount.toString(),
                            selectedCategory,
                            comment,
                            dateTimeArray[0],
                            dateTimeArray[1]
                        )
                        databaseViewModel.addTransaction(newTransaction)

                        navigate()
                    }
                }
            }
        }
    }

    private fun navigate() {
        val act = ThirdfragmentDirections.actionThirdfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
