package com.univation.tdsadmin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WorkoutExerciseObject(val position: String, val exerciseName: String, val sets: String, val reps: String, val rpe: String, val weight: String) : Parcelable {
    constructor() : this ("", "", "", "", "", "")
}