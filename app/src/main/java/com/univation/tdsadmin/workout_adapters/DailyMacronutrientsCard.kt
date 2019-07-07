package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.graphics.Paint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.DailyMacronutrientsObject
import com.univation.tdsadmin.viewcheckins.ChooseWeekActivity
import com.univation.tdsadmin.viewcheckins.ChooseBlockFragment
import com.univation.tdsadmin.viewcheckins.ViewWorkoutWeekActivityForCheckIns
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_daily_macro_row.view.*

class DailyMacronutrientsCard(val key: String, val dailyMacronutrientsObject: DailyMacronutrientsObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.protein_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.carbohydrates_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.fats_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.calories_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.weight_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        if(dailyMacronutrientsObject.protein.isEmpty()){
            viewHolder.itemView.protein_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.protein_input_text.text = dailyMacronutrientsObject.protein
        }

        if(dailyMacronutrientsObject.carbohydrates.isEmpty()){
            viewHolder.itemView.carbohydrates_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.carbohydrates_input_text.text = dailyMacronutrientsObject.carbohydrates
        }

        if(dailyMacronutrientsObject.fats.isEmpty()){
            viewHolder.itemView.fats_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.fats_input_text.text = dailyMacronutrientsObject.fats
        }

        if(dailyMacronutrientsObject.calories.isEmpty()){
            viewHolder.itemView.calories_input_text.text = "#"
        }
        else{
            viewHolder.itemView.calories_input_text.text = dailyMacronutrientsObject.calories
        }

        if(dailyMacronutrientsObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_text.text = "#"
        }
        else{
            viewHolder.itemView.weight_input_text.text = dailyMacronutrientsObject.weight
        }
    }

    override fun getLayout(): Int {
        return R.layout.workout_daily_macro_row
    }
}