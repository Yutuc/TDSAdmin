package com.univation.tdsadmin.view_daily_macros_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.DailyMacronutrientsAveragesObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.daily_macro_averages_row.view.*

class DailyMacronutrientsAveragesCard (val dailyMacronutrientsAveragesObject: DailyMacronutrientsAveragesObject) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.daily_macro_averages_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.protein_average_text.text = dailyMacronutrientsAveragesObject.protein
        viewHolder.itemView.carbohydrates_average_text.text = dailyMacronutrientsAveragesObject.carbohydrates
        viewHolder.itemView.fat_average_text.text = dailyMacronutrientsAveragesObject.fat
        viewHolder.itemView.calories_average_text.text = dailyMacronutrientsAveragesObject.calories
        viewHolder.itemView.weight_average_text.text = dailyMacronutrientsAveragesObject.weight
    }

}