package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MainExerciseObject(val position: Int, var exerciseName: String, val sets: String, val reps: String, val rpe: String, val weight: String, var url: String) : Parcelable {
    constructor() : this (-1, "", "", "", "", "", "")
}