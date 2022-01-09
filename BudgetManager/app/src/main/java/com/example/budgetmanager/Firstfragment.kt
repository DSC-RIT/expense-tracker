package com.example.budgetmanager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.databinding.FirstFragmentBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.android.synthetic.main.second_fragment.view.*

class
Firstfragment:Fragment() {
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var binding:FirstFragmentBinding
    private lateinit var pieChart: PieChart
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseViewModel.initial(requireNotNull(this.activity).application)
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

        pieChart = binding.pieChart
        setUpPieChart();
        loadPieChartData();
    }

    private fun setUpPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(true)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        pieChart.legend.isWordWrapEnabled = true
    }

    private fun loadPieChartData(){
        pieChart.setUsePercentValues(true)

        var foodSum = 0.0;
        var grocerySum = 0.0;
        var stationarySum = 0.0;
        var rechargeSum = 0.0;
        var travelSum = 0.0;
        var clothingSum = 0.0;
        var leisureSum = 0.0;
        var otherSum = 0.0;
        var totalSum = 0.0;

        databaseViewModel.allTransactions.observe(viewLifecycleOwner) {
            for (transaction in it) {
                when (transaction.category) {
                    "Food" -> foodSum += transaction.amount.toInt();
                    "Grocery" -> grocerySum += transaction.amount.toInt();
                    "Stationary" -> stationarySum += transaction.amount.toInt();
                    "Recharge" -> rechargeSum += transaction.amount.toInt();
                    "Travelling" -> travelSum += transaction.amount.toInt();
                    "Clothing" -> clothingSum += transaction.amount.toInt();
                    "Leisure" -> leisureSum += transaction.amount.toInt();
                    "Others" -> otherSum += transaction.amount.toInt();
                }
            }
            totalSum =
                foodSum + grocerySum + stationarySum + rechargeSum + travelSum + clothingSum + leisureSum + otherSum;


            val dataEntries = ArrayList<PieEntry>()
            dataEntries.add(PieEntry((foodSum / totalSum).toFloat(), "Food"))
            dataEntries.add(PieEntry((grocerySum / totalSum).toFloat(), "Grocery"))
            dataEntries.add(PieEntry((stationarySum / totalSum).toFloat(), "Stationary"))
            dataEntries.add(PieEntry((rechargeSum / totalSum).toFloat(), "Recharge"))
            dataEntries.add(PieEntry((travelSum / totalSum).toFloat(), "Travelling"))
            dataEntries.add(PieEntry((clothingSum / totalSum).toFloat(), "Clothing"))
            dataEntries.add(PieEntry((leisureSum / totalSum).toFloat(), "Leisure"))
            dataEntries.add(PieEntry((otherSum / totalSum).toFloat(), "Others"))

            val colors: ArrayList<Int> = ArrayList()
            colors.add(Color.parseColor("#ff1496"))
            colors.add(Color.parseColor("#26a2f0"))
            colors.add(Color.parseColor("#1ab745"))
            colors.add(Color.parseColor("#f9d700"))
            colors.add(Color.parseColor("#fe7c01"))
            colors.add(Color.parseColor("#f10600"))
            colors.add(Color.parseColor("#e422f5"))
            colors.add(Color.parseColor("#4a21ed"))

            val dataSet = PieDataSet(dataEntries, "")
            val data = PieData(dataSet)

            // In Percentage
            data.setValueFormatter(PercentFormatter())
            dataSet.sliceSpace = 3f
            dataSet.colors = colors
            pieChart.data = data
            data.setValueTextSize(15f)
            pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
            pieChart.animateY(1400, Easing.EaseInOutQuad)

            //create hole in center
            pieChart.holeRadius = 58f
            pieChart.transparentCircleRadius = 61f
            pieChart.isDrawHoleEnabled = true
            pieChart.setHoleColor(Color.WHITE)


            //add text in center
            pieChart.setDrawCenterText(true);
            pieChart.centerText = "Expenditure"


            pieChart.invalidate()
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
