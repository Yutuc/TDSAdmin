package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AccessoryExerciseObject (val position: Int, val exerciseName: String, val sets: String, val reps: String, val url: String) :
    Parcelable {
    constructor() : this (-1, "", "", "", "")
}