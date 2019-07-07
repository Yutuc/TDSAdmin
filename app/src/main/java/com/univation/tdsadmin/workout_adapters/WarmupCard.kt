package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WarmupExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.warmup_card.view.*

class WarmupCard(val warmupArrayList: ArrayList<WarmupExerciseObject>): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val warmupCardAdapter = GroupAdapter<ViewHolder>()
        warmupCardAdapter.add(WarmupTitlesRow())
        warmupArrayList.forEach {
            warmupCardAdapter.add(WarmupExerciseRow(it))
        }
        viewHolder.itemView.warmup_card_recyclerview.adapter = warmupCardAdapter
    }

    override fun getLayout(): Int {
        return R.layout.warmup_card
    }
}

