package com.univation.tdsadmin

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row.view.*

class UserRow(val user: UserObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.user_name_textview.text = user.firstName + " " + user.lastName
    }

    override fun getLayout(): Int {
        return R.layout.user_row
    }
}