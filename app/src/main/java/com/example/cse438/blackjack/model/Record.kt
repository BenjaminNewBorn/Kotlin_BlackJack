package com.example.cse438.blackjack.model

import com.example.cse438.blackjack.enum.GameResult
import java.io.Serializable

class Record():  Serializable {
    var game_id: Int = 0
    var dealer_record: ArrayList<Int> = ArrayList()
    var player_record: ArrayList<Int> = ArrayList()
    var isWin:GameResult = GameResult.Win

    constructor(
        game_id:Int,
        dealer_record: ArrayList<Int>,
        plarer_record:ArrayList<Int>,
        isWin:GameResult
    ): this() {
        this.game_id = game_id
        this.dealer_record = dealer_record
        this.player_record = plarer_record
        this.isWin = isWin
    }
}