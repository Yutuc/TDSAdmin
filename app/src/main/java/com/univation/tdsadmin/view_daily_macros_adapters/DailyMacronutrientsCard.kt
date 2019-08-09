package com.univation.tdsadmin.view_daily_macros_adapters

import android.graphics.Paint
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.DailyMacronutrientsObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.daily_macro_row.view.*

class DailyMacronutrientsCard(val dailyMacronutrientsObject: DailyMacronutrientsObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.date_textview_daily_macro_row.text = dailyMacronutrientsObject.date

        viewHolder.itemView.protein_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.carbohydrates_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.fat_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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

        if(dailyMacronutrientsObject.fat.isEmpty()){
            viewHolder.itemView.fat_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.fat_input_text.text = dailyMacronutrientsObject.fat
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
        return R.layout.daily_macro_row
    }
}