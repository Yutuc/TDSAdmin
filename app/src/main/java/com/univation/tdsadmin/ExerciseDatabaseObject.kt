package com.univation.tdsadmin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ExerciseDatabaseObject (val exerciseName: String, val videoUrl: String) : Parcelable{
    constructor() : this("", "")
}