package com.univation.tdsadmin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WarmupExerciseObject(val exerciseName: String, val sets: String, val reps: String, val rpe: String) : Parcelable {
    constructor() : this ("", "", "", "")
}