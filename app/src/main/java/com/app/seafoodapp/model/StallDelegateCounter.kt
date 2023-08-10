package com.app.seafoodapp.model

class StallDelegateCounter {
    var stallName: String? = null
    var maxCounter = 0
    var currentCounter = 0

    constructor() {}
    constructor(stallName: String?, maxCounter: Int, currentCounter: Int) {
        this.stallName = stallName
        this.maxCounter = maxCounter
        this.currentCounter = currentCounter
    }
}



