package com.univation.tdsadmin.view_user_nutrition_adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.protein_choices_column.view.*

class ProteinChoicesColumn(val foodChoices: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.protein_choices_column
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.food_choices_textview_protein_column.text = foodChoices
    }

}