package com.univation.tdsadmin.view_user_food_choices_adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fat_choices_column.view.*

class FatChoicesColumn(val foodChoices: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.fat_choices_column
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.fat_title_textview_fat_choices_column.text = foodChoices
    }

}