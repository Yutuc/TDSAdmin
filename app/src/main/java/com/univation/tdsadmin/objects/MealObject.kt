package com.univation.tdsadmin.objects

class MealObject(val key: String, val mealName: String, val protein: String, val carbohydrates: String, val fat: String, val vegetables: String){
    constructor() : this("", "", "", "", "", "")
}