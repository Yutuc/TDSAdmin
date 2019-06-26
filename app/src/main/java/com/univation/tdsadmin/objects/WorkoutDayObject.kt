package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WorkoutDayObject (val position: Int, var mainArrayList: ArrayList<MainExerciseObject>?, var warmupArrayList: ArrayList<WarmupExerciseObject>?, var accessoryArrayList: ArrayList<AccessoryExerciseObject>?, var coreArrayList: ArrayList<CoreExerciseObject>?, var conditioningArrayList: ArrayList<ConditioningExerciseObject>?) : Parcelable {
    constructor() : this(-1, null, null, null, null, null)
}