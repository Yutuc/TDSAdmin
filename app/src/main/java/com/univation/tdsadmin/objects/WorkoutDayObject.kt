package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WorkoutDayObject (val position: Int, var mainWorkoutArrayList: ArrayList<MainExerciseObject>?, var warmupArrayList: ArrayList<WarmupExerciseObject>?, var accessoryArrayList: ArrayList<AccessoryExerciseObject>?) : Parcelable {
    constructor() : this(-1, null, null, null)
}