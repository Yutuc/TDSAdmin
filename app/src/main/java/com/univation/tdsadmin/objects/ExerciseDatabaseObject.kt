package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ExerciseDatabaseObject (val exerciseName: String, val videoUrl: String) : Parcelable{
    constructor() : this("", "")
}