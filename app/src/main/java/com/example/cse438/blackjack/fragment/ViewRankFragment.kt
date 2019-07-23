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
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.adapter.rankAdapter
import com.example.cse438.blackjack.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_view_rank.*
import kotlinx.android.synthetic.main.rank_detail.view.*
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class ViewRankFragment (context: Context): Fragment() {
    private var parentContext = context
    private var listInitialized = false
    private var rank_list: ArrayList<User> = ArrayList()
    private var adapter = RankAdapter(rank_list)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_rank, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
        rank_list.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("users").orderBy("win_rate", Query.Direction.DESCENDING).limit(15)
            .get()
            .addOnSuccessListener { documents->
                for (document in documents) {
                    var user = User(document.get("username").toString(), document.get("win_rate").toString().toDouble())
                    rank_list.add(user)
                }
                rank_list_recycle.apply {
                    rank_list_recycle.layoutManager = LinearLayoutManager(parentContext)
                    rank_list_recycle.addItemDecoration(DividerItemDecoration(parentContext, DividerItemDecoration.VERTICAL))
                    adapter = RankAdapter(rank_list)
                    rank_list_recycle.adapter = adapter
                    Log.d("rank", rank_list_recycle.childCount.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("get rank failure", "Error getting documents: ", exception)
            }





//        var adapter = rankAdapter(parentContext, android.R.layout.simple_list_item_1, rank_list.toTypedArray())

    }

    inner class RankAdapter(private val list:ArrayList<User>): RecyclerView.Adapter<RankAdapter.RankViewHolder>() {
        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(p0: RankViewHolder, p1: Int) {
            var user = list[p1]
            p0.rankUserName.text = user.name
            var percent = NumberFormat.getPercentInstance()
            percent.minimumFractionDigits = 1
            p0.rankWinRate.text = percent.format(user.winRate).toString()
        }
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RankViewHolder {
            var itemView = LayoutInflater.from(p0.context).inflate(R.layout.rank_detail,p0,false)
            return RankViewHolder(itemView)
        }

        inner class RankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var row = itemView

            var rankUserName: TextView = itemView.rank_user_name
            var rankWinRate: TextView = itemView.rank_win_rate
        }
    }
}