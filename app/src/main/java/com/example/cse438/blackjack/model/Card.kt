package com.example.cse438.blackjack.model

import java.io.Serializable

class Card() : Serializable {
    private var name:String = ""
    private var number: Int = 0
    private var id: Int = 0
    var isAce: Boolean = false

    constructor(
        name:String,
        id: Int
    ): this(){
        this.name = name
        this.id = id
        getNumberFromName(name)
    }

    private fun getNumberFromName(cardName: String) {
        if(cardName.contains("ace")) {
            this.number = 1
            this.isAce = true
        } else if(cardName.contains("2")) {
            this.number = 2
        }else if(cardName.contains("3")) {
            this.number = 3
        }else if(cardName.contains("4")) {
            this.number = 4
        }else if(cardName.contains("5")) {
            this.number = 5
        }else if(cardName.contains("6")) {
            this.number = 6
        }else if(cardName.contains("7")) {
            this.number = 7
        }else if(cardName.contains("8")) {
            this.number = 8
        }else if(cardName.contains("9")) {
            this.number = 9
        } else {
            this.number = 10
        }
    }

    fun returnName(): String {
        return this.name
    }

    fun returnId(): Int {
        return this.id
    }

    fun returnNumber(): Int {
        return this.number
    }

}