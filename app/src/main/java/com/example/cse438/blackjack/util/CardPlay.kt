package com.example.cse438.blackjack.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.cse438.blackjack.App
import com.example.cse438.blackjack.enum.GameResult
import com.example.cse438.blackjack.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class CardPlay {
    companion object {

        val db = FirebaseFirestore.getInstance()
        val userId = App.firebaseAuth?.currentUser?.uid

//        val randomizer: CardRandomizer = CardRandomizer()
//        var cardList: ArrayList<Int> = ArrayList()
//        var rand: Random = Random()

//        var r: Int = 0
//        var id:Int = 0
//        var name: String = ""

        private var game_times:Int = 0
        private var win:Int = 0
        private var lose:Int = 0

        fun getUserRecord() {
            db.document("users/$userId").get().addOnCompleteListener {
                win = it.result?.get("win").toString().toInt()
                lose = it.result?.get("lose").toString().toInt()
                game_times = it.result?.get("game_time").toString().toInt()
            }
        }



//        fun createANewGame(context: Context){
//            game_times++
//        }

        fun winAGame() {
            win++
            game_times++
        }

        fun loseAGame() {
            lose++
            game_times++
        }

        fun passAGame() {
            game_times++
        }

        fun writeData(context: Context) {
            var document = db.document("users/$userId")
            document.update("win", win)
            document.update("lose", lose)
            document.update("win_rate", win.toDouble() / game_times)
            document.update("game_time", game_times)
                .addOnSuccessListener {
//                    Toast.makeText(context, "Success to update user data", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{

            }
        }

        fun writeGameRecord(context: Context,player_list:ArrayList<Card>, dealer_list:ArrayList<Card>, game_result:GameResult) {
            var document = db.document("records/$userId").collection("records").document("$game_times")

            var playerCardIdList: ArrayList<Int> = ArrayList()
            var dealerCardIdList: ArrayList<Int> = ArrayList()
            for (card in player_list) {
                playerCardIdList.add(card.returnId())
            }
            for (card in dealer_list) {
                dealerCardIdList.add(card.returnId())
            }


            val recordData = HashMap<String, Any>()
            recordData["game_id"] = game_times
            recordData["player_list"] = playerCardIdList
            recordData["dealer_list"] = dealerCardIdList
            recordData["game_result"] = game_result

            document.set(recordData)
                .addOnSuccessListener {
                    Log.d("add Record Data", "success")
                }
                .addOnFailureListener{
                    Log.d("add Record Data", "Fail")
                }
        }

        fun determineBust(list: ArrayList<Card>): Boolean {
            if(getMaxValue(list) > 21) {
                return true
            }
            return false
        }

//        fun determineStay(list: ArrayList<Card>): Boolean {
//            var count:Int = 0
//            for( c in list)  {
//                if(c.isAce) {
//                    count = count + 11;
//                } else {
//                    count = count - c.returnNumber()
//                }
//                if(count < 0) {
//                    return false
//                }
//            }
//            return true
//        }

        fun getMaxValue(list:ArrayList<Card>): Int {
            var count: Int = 0
            var nAce = getNumOfAce(list)
            var sum = 0;
            for( c in list) {
                sum += c.returnNumber()
            }
            if(nAce != 0) {
                if (sum + 10 <= 21) {
                    return sum + 10
                }
            }
            return sum

        }

        fun getNumOfAce(list:ArrayList<Card>):Int {
            var count:Int = 0
            for (i in list) {
                if(i.isAce) {
                    count++
                }
            }
            return count
        }
    }

}