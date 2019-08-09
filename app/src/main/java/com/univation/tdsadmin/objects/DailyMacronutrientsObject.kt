package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DailyMacronutrientsObject(val key: String, val date: String, val protein: String, val carbohydrates: String, val fat: String, val calories: String, val weight: String) : Parcelable{
    constructor() :this("", "", "", "", "", "", "")
}