package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserObject(val uid: String, val email: String, val firstName: String, val lastName: String) : Parcelable {
    constructor() : this ("", "", "", "")
}