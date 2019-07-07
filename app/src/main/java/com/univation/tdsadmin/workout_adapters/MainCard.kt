package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_card.view.*

class MainCard (val key: String, val mainArrayList: ArrayList<MainExerciseObject>) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val mainCardAdapter = GroupAdapter<ViewHolder>()
        mainCardAdapter.add(MainTitlesRow())
        mainArrayList.forEach {
            mainCardAdapter.add(MainExerciseRow(key, it))
        }
        viewHolder.itemView.main_card_recyclerview.adapter = mainCardAdapter
    }

    override fun getLayout(): Int {
        return R.layout.main_card
    }
}