package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WarmupExerciseObject(val exerciseName: String, val sets: String, val reps: String, val rpe: String, val type: String, val url: String) : Parcelable {
    constructor() : this ("", "", "", "", "", "")
}