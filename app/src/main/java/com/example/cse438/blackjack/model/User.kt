package com.example.cse438.blackjack.model

import java.io.Serializable

class User(): Serializable {
    var name: String = ""
    var winRate: Double = 0.0
    constructor(
        name:String,
        rate: Double
    ): this() {
        this.name = name
        this.winRate = rate
    }
}