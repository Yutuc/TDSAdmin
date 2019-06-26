package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ConditioningExerciseObject(val position: Int, val exerciseName: String, val minutes: String, val seconds: String, val url: String) :
    Parcelable {
    constructor() : this (-1, "", "", "", "")
}