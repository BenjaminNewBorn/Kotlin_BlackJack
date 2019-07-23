package com.example.cse438.blackjack.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cse438.blackjack.App
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.enum.GameResult
import com.example.cse438.blackjack.model.Card
import com.example.cse438.blackjack.model.Record
import com.example.cse438.blackjack.model.User
import com.example.cse438.blackjack.util.CardPlay
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_view_rank.*
import kotlinx.android.synthetic.main.fragment_view_record.*
import kotlinx.android.synthetic.main.rank_detail.view.*
import kotlinx.android.synthetic.main.record_list_detail.view.*
import java.text.NumberFormat

@SuppressLint("ValidFragment")
class ViewRecordFragment (context: Context): Fragment() {
    private var parentContext = context
    private var record_list: ArrayList<Record> = ArrayList()
    private var adapter = RecordAdapter(record_list)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_record, container, false)
    }

    override fun onStart() {
        super.onStart()
        record_list.clear()
        val db = FirebaseFirestore.getInstance()
        val userId = App.firebaseAuth?.currentUser?.uid
        var collection = db.document("records/${CardPlay.userId}").collection("records")
        collection.orderBy("game_id", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    var game_id:Int = document.get("game_id").toString().toInt()
                    var dealerCardList: ArrayList<Int> = document.get("dealer_list") as ArrayList<Int>
                    var playerCardList: ArrayList<Int> = document.get("player_list") as ArrayList<Int>
                    var gr = document.get("game_result")
                    var game_result: GameResult = GameResult.InComplete
                    when(gr) {
                        "Win" -> game_result = GameResult.Win
                        "Lose" -> game_result = GameResult.Lose
                        "Draw" -> game_result = GameResult.Draw
                        else -> game_result = GameResult.InComplete
                    }

                    var record = Record(game_id, dealerCardList, playerCardList, game_result)
                    record_list.add(record)
                }
                record_recyclerview.layoutManager = LinearLayoutManager(parentContext)
                record_recyclerview.addItemDecoration(DividerItemDecoration(parentContext, DividerItemDecoration.VERTICAL))
                adapter = RecordAdapter(record_list)
                record_recyclerview.adapter = adapter
            }
    }

    inner class RecordAdapter(private val list:ArrayList<Record>): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(p0: RecordViewHolder, p1: Int) {
            var record = list[p1]
            p0.GameResultTv.text = record.isWin.toString()
            for(i in record.dealer_record.indices) {
                when(i){
                    0 -> p0.dealerImg1.setImageResource(record.dealer_record.get(i))
                    1 -> p0.dealerImg2.setImageResource(record.dealer_record.get(i))
                    2 -> p0.dealerImg3.setImageResource(record.dealer_record.get(i))
                    3 -> p0.dealerImg4.setImageResource(record.dealer_record.get(i))
                    4 -> p0.dealerImg5.setImageResource(record.dealer_record.get(i))
                    5 -> p0.dealerImg6.setImageResource(record.dealer_record.get(i))
                    6 -> p0.dealerImg7.setImageResource(record.dealer_record.get(i))
                }
            }

            for(i in record.player_record.indices) {
                when(i){
                    0 -> p0.playerImg1.setImageResource(record.player_record.get(i))
                    1 -> p0.playerImg2.setImageResource(record.player_record.get(i))
                    2 -> p0.playerImg3.setImageResource(record.player_record.get(i))
                    3 -> p0.playerImg4.setImageResource(record.player_record.get(i))
                    4 -> p0.playerImg5.setImageResource(record.player_record.get(i))
                    5 -> p0.playerImg6.setImageResource(record.player_record.get(i))
                    6 -> p0.playerImg7.setImageResource(record.player_record.get(i))
                }
            }
        }
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecordViewHolder {
            var itemView = LayoutInflater.from(p0.context).inflate(R.layout.record_list_detail,p0,false)
            return RecordViewHolder(itemView)
        }

        inner class RecordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var row = itemView

            var GameResultTv: TextView = itemView.game_result_tv
            var dealerImg1: ImageView = itemView.dealerCard_1
            var dealerImg2: ImageView = itemView.dealerCard_2
            var dealerImg3: ImageView = itemView.dealerCard_3
            var dealerImg4: ImageView = itemView.dealerCard_4
            var dealerImg5: ImageView = itemView.dealerCard_5
            var dealerImg6: ImageView = itemView.dealerCard_6
            var dealerImg7: ImageView = itemView.dealerCard_7


            var playerImg1: ImageView = itemView.playerCard_1
            var playerImg2: ImageView = itemView.playerCard_
            var playerImg3: ImageView = itemView.playerCard_2
            var playerImg4: ImageView = itemView.playerCard_3
            var playerImg5: ImageView = itemView.playerCard_4
            var playerImg6: ImageView = itemView.playerCard_5
            var playerImg7: ImageView = itemView.playerCard_6

            var dealerList: ArrayList<ImageView> = ArrayList()

        }
    }
}