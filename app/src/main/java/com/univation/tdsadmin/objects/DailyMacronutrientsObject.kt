package com.univation.tdsadmin.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DailyMacronutrientsObject(val protein: String, val carbohydrates: String, val fats: String, val calories: String, val weight: String) :
    Parcelable {
    constructor() :this("", "", "", "", "")
}