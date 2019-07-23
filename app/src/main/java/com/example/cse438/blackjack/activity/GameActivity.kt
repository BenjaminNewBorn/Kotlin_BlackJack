package com.example.cse438.blackjack.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.enum.GameResult
import com.example.cse438.blackjack.model.Card
import com.example.cse438.blackjack.util.CardPlay
import com.example.cse438.blackjack.util.CardRandomizer
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GameActivity:  AppCompatActivity() {
    //prepare card
    val randomizer: CardRandomizer = CardRandomizer()
    var cardList: ArrayList<Int> = ArrayList()
    var rand: Random = Random()
    var r: Int = 0
    var cardId:Int = 0
    var cardName: String = ""
    var index: Int = 0
    var isEnd:Boolean = false
    var gameResult:GameResult = GameResult.Win

    //prepare user

    private var playerCardList :ArrayList<Card> = ArrayList()
    private var playerIndexList :ArrayList<Int> = ArrayList()
    private var dealerCardList :ArrayList<Card> = ArrayList()
    private var dealerIndexList :ArrayList<Int> = ArrayList()

    //prepare img
    private lateinit var mDetector: GestureDetectorCompat
    private var height: Int = 0
    private var width: Int = 0
    private lateinit var faceView: View
    private var playerLocationX = 0
    private var dealerLocationX = 0


    private var imageList: ArrayList<Int> = ArrayList()
    private var margin:Int = 70

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mDetector = GestureDetectorCompat(this, MyGestureLister())

        createImageList()
        cardList = randomizer.getIDs(this) as java.util.ArrayList<Int>

        val metrics = this.resources.displayMetrics
        this@GameActivity.height = metrics.heightPixels
        this@GameActivity.width = metrics.widthPixels

        CardPlay.getUserRecord()
        startANewGame()
        restart_game_button.setOnClickListener{

//            r = rand.nextInt(cardList.size)
//            cardId = cardList.get(r)
//            cardName = resources.getResourceEntryName(cardId)
//            var imageView = findViewById<ImageView>(R.id.card_1)
//            imageView.setImageResource(cardId);
            startANewGame()
        }

    }

    fun startANewGame(){
        rand = Random()
        playerBackCard()
        dealerBackCard()
        index = 0
        playerGetNewCard()
        playerGetNewCard()
        dealerGetNewCard()
        dealerGetNewCard()
        restart_game_button.visibility = View.INVISIBLE
        isEnd = false
    }

    fun playerGetNewCard() {
        var card = getOneCard(this)
        var imageView = findViewById<ImageView>(imageList.get(index))
        playerIndexList.add(index)
        index++
        faceView = imageView
        moveTo( -3 * this@GameActivity.width / 4f + 110 + playerCardList.size * margin,this@GameActivity.height / 2f - 160 - 300 )
        imageView.setImageResource(cardId)
        playerCardList.add(card)
    }

    fun dealerGetNewCard() {
        var card = getOneCard(this)
        var imageView = findViewById<ImageView>(imageList.get(index))
        var mi = imageView.x
        dealerIndexList.add(index)
        index++
        faceView = imageView
        moveTo( -3 * this@GameActivity.width / 4f +110 + dealerCardList.size * margin,-this@GameActivity.height / 2f + 160 + 200)
        if(dealerCardList.size == 0) {
            imageView.setImageResource(cardId)
        }
        dealerCardList.add(card)
    }

    fun playerBackCard(){
        var count = 0
        for( i in playerIndexList) {
            var imageView = findViewById<ImageView>(imageList.get(i))
            imageView.setImageResource(R.drawable.back)
            faceView = imageView
            moveTo( 3 * this@GameActivity.width / 4f - 110 - count  * margin,- this@GameActivity.height / 2f + 160 + 300 )
            playerCardList.remove(playerCardList.last())
            count++
        }
        playerIndexList.clear()
    }

    fun dealerBackCard(){
        var count = 0
        for( i in dealerIndexList) {
            var imageView = findViewById<ImageView>(imageList.get(i))
            imageView.setImageResource(R.drawable.back)
            faceView = imageView
            moveTo( 3 * this@GameActivity.width / 4f - 110 - count * margin,this@GameActivity.height / 2f - 160 - 300 )
            dealerCardList.remove(dealerCardList.last())
            count++
        }
        dealerIndexList.clear()
    }

    private fun dealerTurn(){
        while(CardPlay.getMaxValue(dealerCardList) < 17) {
            dealerGetNewCard()

        }
        Executors.newSingleThreadScheduledExecutor().schedule({
            var count = 0
            for(i in dealerIndexList) {
                var card = dealerCardList.get(count)
                var imageView = findViewById<ImageView>(imageList.get(i))
                imageView.setImageResource(card.returnId())
                count++
            }
        }, 1, TimeUnit.SECONDS)



        if(CardPlay.determineBust(dealerCardList)) {
            var toast = Toast.makeText(this, "Dealer Bust! You Win!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show()
            gameResult = GameResult.Win
            isEnd = true
            CardPlay.winAGame()

        } else if(CardPlay.getMaxValue(dealerCardList) == 21){
            var toast = Toast.makeText(this, "Dealer get 21 points! You Lose!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show()
            isEnd = true
            gameResult = GameResult.Lose
            CardPlay.loseAGame()
        }else if(CardPlay.getMaxValue(dealerCardList) > CardPlay.getMaxValue(playerCardList)){
            var toast = Toast.makeText(this, "Dealer have larger points! You Lose!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show()
            gameResult = GameResult.Lose
            isEnd = true
            CardPlay.loseAGame()
        } else if(CardPlay.getMaxValue(dealerCardList) < CardPlay.getMaxValue(playerCardList)){
            var toast = Toast.makeText(this, "You have larger points! You Win!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show()
            gameResult = GameResult.Win
            isEnd = true
            CardPlay.winAGame()
        } else {
            var toast = Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show()
            gameResult = GameResult.Draw
            isEnd = true
            CardPlay.passAGame()
        }
        CardPlay.writeData(this)
        CardPlay.writeGameRecord(this@GameActivity, playerCardList, dealerCardList,gameResult)

        restart_game_button.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        this.finish()
    }

    private fun getOneCard(context: Context): Card {

        r = rand.nextInt(cardList.size)
        cardId = cardList.get(r)
        cardName = resources.getResourceEntryName(cardId)
        val card =  Card(cardName, cardId)
        return card;
    }

    fun moveTo(targetX: Float, targetY: Float) {
        val animSetXY = AnimatorSet()

        var  x = ObjectAnimator.ofFloat(
            faceView, "translationX",
            faceView.translationX,
            targetX
        )
//        x.start()

        var  y = ObjectAnimator.ofFloat(
            faceView, "translationY",
            faceView.translationY,
            targetY
        )

//        animSetXY.playTogether(x,y);
        animSetXY.playTogether(x,y)
        animSetXY.duration
        animSetXY.start()
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event) //Always do what the super code will do since it will not break what other code want to do
    }
    private fun createImageList() {
        imageList.add(R.id.card_1)
        imageList.add(R.id.card_2)
        imageList.add(R.id.card_3)
        imageList.add(R.id.card_4)
        imageList.add(R.id.card_5)
        imageList.add(R.id.card_6)
        imageList.add(R.id.card_7)
        imageList.add(R.id.card_8)
        imageList.add(R.id.card_9)
        imageList.add(R.id.card_10)
        imageList.add(R.id.card_11)
        imageList.add(R.id.card_12)
        imageList.add(R.id.card_13)
        imageList.add(R.id.card_14)
        imageList.add(R.id.card_15)
        imageList.add(R.id.card_16)
        imageList.add(R.id.card_17)
        imageList.add(R.id.card_18)
        imageList.add(R.id.card_19)
        imageList.add(R.id.card_20)
        imageList.add(R.id.card_21)
        imageList.add(R.id.card_22)
        imageList.add(R.id.card_23)
        imageList.add(R.id.card_24)
        imageList.add(R.id.card_25)
        imageList.add(R.id.card_26)
        imageList.add(R.id.card_27)
        imageList.add(R.id.card_28)
        imageList.add(R.id.card_29)
        imageList.add(R.id.card_30)
        imageList.add(R.id.card_31)
        imageList.add(R.id.card_32)
        imageList.add(R.id.card_33)
        imageList.add(R.id.card_34)
        imageList.add(R.id.card_35)
        imageList.add(R.id.card_36)
        imageList.add(R.id.card_37)
        imageList.add(R.id.card_38)
        imageList.add(R.id.card_39)
        imageList.add(R.id.card_40)
        imageList.add(R.id.card_41)
        imageList.add(R.id.card_42)
        imageList.add(R.id.card_43)
        imageList.add(R.id.card_44)
        imageList.add(R.id.card_45)
        imageList.add(R.id.card_46)
        imageList.add(R.id.card_47)
        imageList.add(R.id.card_48)
        imageList.add(R.id.card_49)
        imageList.add(R.id.card_50)
        imageList.add(R.id.card_51)
        imageList.add(R.id.card_52)
    }

    private inner class MyGestureLister : GestureDetector.SimpleOnGestureListener() {
        var swipedistance = 150

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if(isEnd) {
                var toast = Toast.makeText(this@GameActivity, "This game has end, please click button to start a new game!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show()
            } else {
                dealerTurn()
            }
            return true
        }


        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

            if(e2.x - e1.x > swipedistance) {

                if(isEnd) {
                    var toast = Toast.makeText(this@GameActivity, "This game has end, please click button to start a new game!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show()
                } else {
                    playerGetNewCard()
                    if(CardPlay.determineBust(playerCardList)) {
                        var toast = Toast.makeText(this@GameActivity, "You Bust! You Lose!", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show()
                        gameResult = GameResult.Lose
                        isEnd = true
                        CardPlay.loseAGame()
                        CardPlay.writeData(this@GameActivity)
                        CardPlay.writeGameRecord(this@GameActivity, playerCardList, dealerCardList,gameResult)
                        restart_game_button.visibility = View.VISIBLE
                    }
                }
                return true
            }
            return false
        }
    }
}