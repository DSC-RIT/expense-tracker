package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.databinding.SecondFragmentBinding

class Secondfragment: Fragment() {
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var binding: SecondFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseViewModel.initial(requireNotNull(this.activity).application)
        binding = DataBindingUtil.inflate(inflater, R.layout.second_fragment, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val prevFragmentName = arguments?.get("id") ?: "none"

        if (prevFragmentName == "none") {
            databaseViewModel.budget.observe(viewLifecycleOwner) { budgets ->
                if (budgets.isEmpty()) {
                    validate()
                } else {
                    navigate()
                }
            }
        }
        else {
            validate()
        }
    }

    private fun validate() {
        val btn = binding.doneLimitButton

        btn.setOnClickListener {
            val setBudget = binding.limitTotalBudgetEditText.text.toString()
            val budget: Int

            if (setBudget.isEmpty()) {
                Toast.makeText(
                    activity,
                    "Please enter your monthly budget first!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                budget = setBudget.toIntOrNull() ?: 0

                if (budget <= 0)
                    Toast.makeText(
                        activity,
                        "Please enter a valid amount!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                else {
                    val categories = ArrayList<String>()
                    categories.add(binding.limitFood.text.toString())
                    categories.add(binding.limitGrocery.text.toString())
                    categories.add(binding.limitStationary.text.toString())
                    categories.add(binding.limitRecharge.text.toString())
                    categories.add(binding.limitTravelling.text.toString())
                    categories.add(binding.limitClothing.text.toString())
                    categories.add(binding.limitLeisure.text.toString())
                    categories.add(binding.limitOthers.text.toString())

                    var sum = 0
                    var flag = false
                    for ((index, category) in categories.withIndex()) {
                        val value: Int? = category.toIntOrNull()

                        if (value == null) {
                            if (category.isEmpty()) categories[index] = "0"
                            else {
                                Toast.makeText(
                                    activity,
                                    "Please enter a valid amount!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                flag = true
                                break
                            }
                        } else {
                            if (value < 0) {
                                Toast.makeText(
                                    activity,
                                    "Please enter a valid amount!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                flag = true
                                break
                            } else {
                                sum += value
                            }
                        }
                    }

                    if (!flag) {
                        if (sum != budget) {
                            Toast.makeText(
                                activity,
                                "Entered amounts doesn't sum up to the entered budget!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val limits = Budget(
                                budget.toString(),
                                categories[0],
                                categories[1],
                                categories[2],
                                categories[3],
                                categories[4],
                                categories[5],
                                categories[6],
                                categories[7],
                            )
                            databaseViewModel.addBudget(limits)

                            navigate()
                        }
                    }
                }
            }
        }
    }

    private fun navigate() {
        val act = SecondfragmentDirections.actionSecondfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
