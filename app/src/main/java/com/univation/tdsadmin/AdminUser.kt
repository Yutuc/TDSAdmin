package com.univation.tdsadmin

class AdminUser(val admin: Boolean, val uid: String, val email: String, val firstName: String, val lastName: String){
    constructor() : this (false, "", "", "", "")
}