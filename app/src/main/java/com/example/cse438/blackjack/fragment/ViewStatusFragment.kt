package com.example.cse438.blackjack.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cse438.blackjack.App
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.activity.GameActivity
import com.example.cse438.blackjack.activity.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_view_status.*

@SuppressLint("ValidFragment")
class ViewStatusFragment (context: Context): Fragment() {
    private var parentContext = context
    private var initialized: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_view_status, container, false)
    }

    override fun onStart() {
        super.onStart()

        if(!this.initialized) {
            val userId = App.firebaseAuth?.currentUser?.uid
            val db = FirebaseFirestore.getInstance()
            db.document("users/$userId").get().addOnCompleteListener {
                vs_username_tv.text = it.result?.get("username") as? String
                vs_win_tv.text = it.result?.get("win").toString() as? String
                vs_lose_tv.text = it.result?.get("lose").toString() as? String
            }

            start_game_btn.setOnClickListener{
                Log.e("Game", "Start")
                val intent = Intent(parentContext, GameActivity::class.java)
                startActivity(intent)
            }

            logout_btn.setOnClickListener{
                App.firebaseAuth?.signOut()
                Log.e("User", "Logout")
                val intent = Intent(parentContext, MainActivity::class.java)
                startActivity(intent)
            }

        }


    }
}