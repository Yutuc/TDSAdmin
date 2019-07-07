package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoreExerciseObject (val position: Int, val exerciseName: String, val sets: String, val reps: String, val weight: String, val url: String) :
    Parcelable {
    constructor() : this (-1, "", "", "", "","")
}