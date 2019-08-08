package com.univation.tdsadmin.view_user_nutrition_adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.carbohydrate_choices_column.view.*

class CarbohydrateChoicesColumn(val foodChoices: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.carbohydrate_choices_column
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.food_choices_textview_carbohydrate_column.text = foodChoices
    }

}