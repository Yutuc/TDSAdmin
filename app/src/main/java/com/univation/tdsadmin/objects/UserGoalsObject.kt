package com.univation.tdsadmin.objects

class UserGoalsObject(val userName: String, val startWeight: String, val goalWeight: String, val protein: String, val carbohydrates: String, val fat: String, val calories: String){
    constructor() : this("", "", "", "", "", "", "")
}