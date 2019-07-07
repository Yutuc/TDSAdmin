package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class AccessoryTitlesRow () : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.accessory_titles_row
    }
}