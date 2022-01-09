package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.databinding.SecondFragmentBinding
import kotlinx.android.synthetic.main.second_fragment.*

class Secondfragment:Fragment() {

    private val databaseViewModel2 : DatabaseViewModel by viewModels()
    private lateinit var binding:SecondFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        databaseViewModel2.initial(requireNotNull(this.activity).application)

        binding=DataBindingUtil.inflate(inflater,R.layout.second_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn=done_limit_button

        btn.setOnClickListener{

            val setBudget = limit_total_budget_edit_text.text.toString()

            val food = limit_food.text.toString()
            val clothes = limit_clothing.text.toString()
            val grocery = limit_grocery.text.toString()
            val leisure = limit_leisure.text.toString()
            val others = limit_others.text.toString()
            val stationary = limit_stationary.text.toString()
            val travelling = limit_travelling.text.toString()
            val recharge = limit_recharge.text.toString()

            val category = ArrayList<String>()
            category.add(food)
            category.add(clothes)
            category.add(grocery)
            category.add(leisure)
            category.add(others)
            category.add(stationary)
            category.add(travelling)
            category.add(recharge)

            var sum = 0

            for (budget in category){
                val value : Int? = budget.toIntOrNull()

                if (value == null){
                    sum += 0
                    continue
                }

                if (value < 0){
                    Toast.makeText(activity, "Enter Valid Limit Input", Toast.LENGTH_SHORT).show()
                }

                else {
                    sum += value
                }
            }

            val budgetValue = setBudget.toInt()

            if (setBudget.isEmpty()){
                Toast.makeText(activity, "Please Select your monthly Budget First", Toast.LENGTH_SHORT).show()
            }

            if (sum != budgetValue){
                Toast.makeText(activity, "Entered Limits are not according to the Budget set", Toast.LENGTH_SHORT).show()
            }

            else{
                val limits =  Budget (setBudget, food, grocery, stationary, recharge, travelling, clothes, leisure, others)
                databaseViewModel2.addBudget(limits)

                method()
            }
        }
    }

    private fun method()
    {
        val act=SecondfragmentDirections.actionSecondfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
