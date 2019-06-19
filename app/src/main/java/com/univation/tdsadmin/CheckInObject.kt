package com.univation.tdsadmin

class CheckInObject (val uid: String, val date: String, val questionOne: String, val questionTwo: String, val questionThree: String,
                     val questionFour: String, val questionFive: String, val questionSix: String, val questionSeven: String){
    constructor() : this ("", "", "", ""
        , "", "", "", "", "")
}