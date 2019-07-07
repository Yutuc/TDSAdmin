package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.AccessoryExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.accessory_card.view.*

class AccessoryCard (val key: String, val accessoryArrayList: ArrayList<AccessoryExerciseObject>) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val accessoryCardAdapter = GroupAdapter<ViewHolder>()
        accessoryCardAdapter.add(AccessoryTitlesRow())
        accessoryArrayList.forEach {
            accessoryCardAdapter.add(AccessoryExerciseRow(key, it))
        }
        viewHolder.itemView.accessory_card_recyclerview.adapter = accessoryCardAdapter
    }

    override fun getLayout(): Int {
        return R.layout.accessory_card
    }
}