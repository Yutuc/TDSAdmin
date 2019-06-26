package com.univation.tdsadmin.adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WarmupExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.warmup_item.view.*

class WarmupWorkoutAdapter(val warmupArrayList: ArrayList<WarmupExerciseObject>): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val warmupItemAdapter = GroupAdapter<ViewHolder>()
        warmupItemAdapter.add(WarmupTitlesRow())
        warmupArrayList.forEach {
            warmupItemAdapter.add(WarmupExerciseRow(it))
        }
        viewHolder.itemView.warmup_item_recyclerview.adapter = warmupItemAdapter
    }

    override fun getLayout(): Int {
        return R.layout.warmup_item
    }
}

