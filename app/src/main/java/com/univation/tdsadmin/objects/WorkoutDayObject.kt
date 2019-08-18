package com.univation.tdsadmin.objects

class WorkoutDayObject (val position: Int, var key: String, val completed: Boolean, var mainArrayList: ArrayList<MainExerciseObject>?, var warmupArrayList: ArrayList<WarmupExerciseObject>?, var accessoryArrayList: ArrayList<AccessoryExerciseObject>?, var coreArrayList: ArrayList<CoreExerciseObject>?, var conditioningArrayList: ArrayList<ConditioningExerciseObject>?) {
    constructor() : this(-1, "", false,null, null, null, null, null)
}