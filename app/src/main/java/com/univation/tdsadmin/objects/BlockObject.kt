package com.univation.tdsadmin.objects

class BlockObject (val key: String, val blockName: String, var size: Int){
    constructor() : this("", "", -1)
}