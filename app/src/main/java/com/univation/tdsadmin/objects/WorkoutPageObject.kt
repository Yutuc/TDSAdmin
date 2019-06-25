package com.univation.tdsadmin.objects

class WorkoutPageObject (val key: String, val date: String, val workoutExercises: ArrayList<MainExerciseObject>
                         , val warmupExercises: ArrayList<WarmupExerciseObject>, val dailyMacronutrientsObject: DailyMacronutrientsObject
)