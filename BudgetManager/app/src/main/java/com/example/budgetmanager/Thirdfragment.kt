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

        rd_group.setOnCheckedChangeListener { rd_group, i ->
            rb = getView()?.findViewById<RadioButton>(i)
        }
        btn.setOnClickListener{
//            The below code is an example for how to insert into the database:
//
//            val entry = Transaction("123", "Food", "Test", "08/01/22", "20:55:30")
//            databaseViewModel.addTransaction(entry)

            val amntSpent = text_amount_spent_edit_text.text.toString()

            if (amntSpent.isEmpty()){
                Toast.makeText(activity, "Please Enter the Amount", Toast.LENGTH_SHORT).show()
            }

            if (rb == null){
                Toast.makeText(activity, "Please Select the Catagory", Toast.LENGTH_SHORT).show()
            }

            else{
                val selectedCategory = rb!!.text
                val comment = TransactionComment.text.toString()

                val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                val dateTimeArray = sdf.format(Date()).toString().split(" ")

                val newTransaction = Transaction(amntSpent, selectedCategory as String, comment, dateTimeArray[0], dateTimeArray[1])
                databaseViewModel.addTransaction(newTransaction)

                method()
            }

        }
    }

    private fun method()
    {
        val act = ThirdfragmentDirections.actionThirdfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
