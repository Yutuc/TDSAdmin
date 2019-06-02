package com.univation.tdsadmin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ScheduledTimeObject (val position: Int, var key: String, val date: String, val time: String, val uid: String) : Parcelable{
    constructor() : this (-1, "", "", "", "")
}