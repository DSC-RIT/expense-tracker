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
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.databinding.FirstFragmentBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class Firstfragment:Fragment() {
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var binding: FirstFragmentBinding
    private lateinit var pieChart: PieChart

    var foodSum: Int = 0
    var grocerySum = 0
    var stationarySum = 0
    var rechargeSum = 0
    var travelSum = 0
    var clothingSum = 0
    var leisureSum = 0
    var otherSum = 0
    var totalSum = 0

    var allottedFoodBudget = 0
    var allottedGroceryBudget = 0
    var allottedStationaryBudget = 0
    var allottedRechargeBudget = 0
    var allottedTravelBudget = 0
    var allottedClothingBudget = 0
    var allottedLeisureBudget = 0
    var allottedOtherBudget = 0

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
        pieChart = binding.pieChart
        foodSum = 0
        grocerySum = 0
        stationarySum = 0
        rechargeSum = 0
        travelSum = 0
        clothingSum = 0
        leisureSum = 0
        otherSum = 0
        totalSum = 0
        allottedFoodBudget = 0
        allottedGroceryBudget = 0
        allottedStationaryBudget = 0
        allottedRechargeBudget = 0
        allottedTravelBudget = 0
        allottedClothingBudget = 0
        allottedLeisureBudget = 0
        allottedOtherBudget = 0

        databaseViewModel.budget.observe(viewLifecycleOwner) { budgets ->
            if (budgets.isNotEmpty()) {
                val budget = budgets[budgets.size - 1]

                allottedFoodBudget = budget.foodBudget.toInt()
                allottedGroceryBudget = budget.groceryBudget.toInt()
                allottedStationaryBudget = budget.stationaryBudget.toInt()
                allottedRechargeBudget = budget.rechargeBudget.toInt()
                allottedTravelBudget = budget.travellingBudget.toInt()
                allottedClothingBudget = budget.clothingBudget.toInt()
                allottedLeisureBudget = budget.leisureBudget.toInt()
                allottedOtherBudget = budget.otherBudget.toInt()
            }

            databaseViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
                for (transaction in transactions) {
                    when (transaction.category) {
                        "Food" -> foodSum += transaction.amount.toInt()
                        "Grocery" -> grocerySum += transaction.amount.toInt()
                        "Stationary" -> stationarySum += transaction.amount.toInt()
                        "Recharge" -> rechargeSum += transaction.amount.toInt()
                        "Travelling" -> travelSum += transaction.amount.toInt()
                        "Clothing" -> clothingSum += transaction.amount.toInt()
                        "Leisure" -> leisureSum += transaction.amount.toInt()
                        "Others" -> otherSum += transaction.amount.toInt()
                    }
                }
                totalSum = foodSum + grocerySum + stationarySum + rechargeSum + travelSum + clothingSum + leisureSum + otherSum;

                if (budgets.isNotEmpty()) {
                    val budget = budgets[budgets.size - 1]

                    binding.valMonthlyBudget.text = budget.budget
                    binding.valTotalSpent.text = totalSum.toString()

                    if (totalSum <= budget.budget.toInt()) {
                        binding.status.text = "You are doing great this month!"
                        binding.status.setTextColor(Color.GREEN)
                    }
                    else {
                        binding.status.text = "Oh no, you crossed your monthly budget!"
                        binding.status.setTextColor(Color.RED)
                    }
                }

                setUpPieChart()
                loadPieChartData()
            }
        }

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

    private fun setUpPieChart() {
        pieChart.apply {
            setUsePercentValues(true)
            description.text = ""
            description.textSize = 24f
            //hollow pie chart
            isDrawHoleEnabled = true
            setTouchEnabled(true)
            setDrawEntryLabels(false)

            //make this true if legend is required
            legend.isEnabled = false
            //adding padding
            setExtraOffsets(20f, 0f, 20f, 20f)
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
//            legend.orientation = Legend.LegendOrientation.HORIZONTAL
//            legend.isWordWrapEnabled = true
        }

        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                pieChart.setDrawEntryLabels(true)
                pieChart.setEntryLabelColor(Color.BLACK)
            }

            override fun onNothingSelected() {
                pieChart.setDrawEntryLabels(false)
            }
        })

    }

    private fun loadPieChartData(){
        pieChart.setUsePercentValues(true)

        val dataEntries = ArrayList<PieEntry>()
        if(foodSum != 0){
            dataEntries.add(PieEntry((foodSum.toFloat() / totalSum.toFloat()), "$foodSum / $allottedFoodBudget food"))
        }
        if(grocerySum != 0){
            dataEntries.add(PieEntry((grocerySum.toFloat()/ totalSum.toFloat()), "$grocerySum / $allottedGroceryBudget grocery"))
        }
        if(stationarySum != 0){
            dataEntries.add(PieEntry((stationarySum.toFloat() / totalSum.toFloat()), "$stationarySum / $allottedStationaryBudget stationary"))
        }
        if(rechargeSum != 0){
            dataEntries.add(PieEntry((rechargeSum.toFloat() / totalSum.toFloat()), "$rechargeSum / $allottedRechargeBudget recharge"))
        }
        if(travelSum != 0){
            dataEntries.add(PieEntry((travelSum.toFloat() / totalSum.toFloat()), "$travelSum / $allottedTravelBudget travel"))
        }
        if(clothingSum != 0){
            dataEntries.add(PieEntry((clothingSum.toFloat() / totalSum.toFloat()), "$clothingSum / $allottedClothingBudget clothing"))
        }
        if(leisureSum != 0){
            dataEntries.add(PieEntry((leisureSum.toFloat() / totalSum.toFloat()), "$leisureSum / $allottedLeisureBudget leisure"))
        }
        if(otherSum != 0){
            dataEntries.add(PieEntry((otherSum.toFloat() / totalSum.toFloat()), "$otherSum / $allottedOtherBudget others"))
        }

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
        pieChart.centerText = "Current Expenditure"
        pieChart.invalidate()
    }

    private fun method1() {
        val act = FirstfragmentDirections.actionFirstfragmentToSecondfragment("pieChartFragment")
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
