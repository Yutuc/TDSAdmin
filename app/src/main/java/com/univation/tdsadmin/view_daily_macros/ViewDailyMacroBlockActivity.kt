package com.univation.tdsadmin.view_daily_macros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.DailyMacronutrientsAveragesObject
import com.univation.tdsadmin.objects.DailyMacronutrientsObject
import com.univation.tdsadmin.view_daily_macros_adapters.DailyMacronutrientsAveragesCard
import com.univation.tdsadmin.view_daily_macros_adapters.DailyMacronutrientsCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_daily_macro_block.*
import java.lang.Exception
import java.nio.file.Files.find
import java.util.ArrayList

class ViewDailyMacroBlockActivity : AppCompatActivity() {

    val blockClicked = SelectDailyMacroBlockActivity.blockClicked
    val adapter = GroupAdapter<ViewHolder>()
    val dailyMacroNutrientsHistoryArrayList = ArrayList<DailyMacronutrientsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_daily_macro_block)
        setTitle("${blockClicked!!.blockName}")

        recyclerview_daily_macros_and_average.adapter = adapter

        pullDailyMacros()
    }

    private fun pullDailyMacros(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList[find(dailyMacronutrientsObject)] = dailyMacronutrientsObject
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList.add(dailyMacronutrientsObject)
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        adapter.add(DailyMacronutrientsAveragesCard(macroAverages()))
        dailyMacroNutrientsHistoryArrayList.forEach {
            adapter.add(DailyMacronutrientsCard(it))
        }
    }//refreshRecyclerView function

    private fun macroAverages() : DailyMacronutrientsAveragesObject {
        var protein = 0f
        var carbohydrates = 0f
        var fat = 0f
        var calories = 0f
        var weight = 0f

        dailyMacroNutrientsHistoryArrayList.forEach {
            try{
                protein += it.protein.toFloat()
            }catch (e: Exception){}

            try{
                carbohydrates += it.carbohydrates.toFloat()
            }catch (e: Exception){}

            try{
                fat += it.fat.toFloat()
            }catch (e: Exception){}

            try{
                calories += it.calories.toFloat()
            }catch (e: Exception){}

            try{
                weight += it.weight.toFloat()
            }catch (e: Exception){}
        }

        protein = protein.div(dailyMacroNutrientsHistoryArrayList.size)
        carbohydrates = carbohydrates.div(dailyMacroNutrientsHistoryArrayList.size)
        fat = fat.div(dailyMacroNutrientsHistoryArrayList.size)
        calories = calories.div(dailyMacroNutrientsHistoryArrayList.size)
        weight = weight.div(dailyMacroNutrientsHistoryArrayList.size)

        val dailyMacronutrientsAveragesObject = DailyMacronutrientsAveragesObject("%.2f".format(protein), "%.2f".format(carbohydrates), "%.2f".format(fat), "%.2f".format(calories), "%.2f".format(weight))
        return dailyMacronutrientsAveragesObject
    }//macroAverages function

    private fun find(dailyMacronutrientsObject: DailyMacronutrientsObject) : Int{
        for (i in dailyMacroNutrientsHistoryArrayList.size-1 downTo 0 step 1) {
            if(dailyMacroNutrientsHistoryArrayList.get(i).key == dailyMacronutrientsObject.key){
                return i
            }
        }
        return -1
    }
}
