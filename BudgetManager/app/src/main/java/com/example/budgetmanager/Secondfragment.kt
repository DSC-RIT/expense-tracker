package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.budgetmanager.databinding.SecondFragmentBinding
import kotlinx.android.synthetic.main.second_fragment.*

class Secondfragment:Fragment() {
    private lateinit var binding:SecondFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.second_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn=done_limit_button
        btn.setOnClickListener{
            method()
        }
    }
    private fun method()
    {
        val act=SecondfragmentDirections.actionSecondfragmentToFirstfragment()
        this.findNavController().navigate(act)
    }
}
