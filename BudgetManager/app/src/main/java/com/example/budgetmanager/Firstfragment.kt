package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.budgetmanager.databinding.FirstFragmentBinding

class
Firstfragment:Fragment() {
    private lateinit var binding:FirstFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn1 = binding.btnSetBudget
        val btn2 = binding.btnAdd
        val btn3 = binding.btnHistory
        btn1.setOnClickListener {
            method1()
        }
        btn2.setOnClickListener {
            method2()
        }
        btn3.setOnClickListener {
            method3()
        }
    }

    private fun method1() {
        val act = FirstfragmentDirections.actionFirstfragmentToSecondfragment()
        this.findNavController().navigate(act)
    }

    private fun method2() {
        val act = FirstfragmentDirections.actionFirstfragmentToThirdfragment()
        this.findNavController().navigate(act)
    }

    private fun method3(){
        val act = FirstfragmentDirections.actionFirstfragmentToFourthfragment()
        this.findNavController().navigate(act)
    }
}
