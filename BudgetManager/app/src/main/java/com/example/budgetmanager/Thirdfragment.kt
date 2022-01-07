package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.budgetmanager.databinding.ThirdFragmentBinding

class Thirdfragment:Fragment() {
       private lateinit var binding: ThirdFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.third_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn=binding.doneAdd
        btn.setOnClickListener{
            method()
        }
    }
    private fun method()
    {
        val act=ThirdfragmentDirections.actionThirdfragment2ToFirstfragment2()
        this.findNavController().navigate(act)
    }
}