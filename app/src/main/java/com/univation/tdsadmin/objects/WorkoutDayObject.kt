package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WorkoutDayObject (val position: Int, var mainWorkoutArrayList: ArrayList<MainExerciseObject>?) : Parcelable {
    constructor() : this(-1, null)
}