package com.univation.tdsapplication.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.BlockObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.block_row.view.*

class BlockRow (val blockObject: BlockObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.block_label_textview.text = "${blockObject.blockName}"
    }

    override fun getLayout(): Int {
        return R.layout.block_row
    }
}