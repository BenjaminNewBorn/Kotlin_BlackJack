package com.example.cse438.blackjack.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cse438.blackjack.App
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.enum.UserInterfaceState
import com.example.cse438.blackjack.fragment.HomeFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
    var currentView = UserInterfaceState.HOME
    var isInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()

        if (App.firebaseAuth == null) {
            App.firebaseAuth = FirebaseAuth.getInstance()
        }

        if (App.firebaseAuth != null && App.firebaseAuth?.currentUser == null) {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
//        ft.commit()
    }


    override fun onStart() {
        super.onStart()
        if (App.firebaseAuth != null && App.firebaseAuth?.currentUser != null){
            Log.e("User", "Logged")
            val intent = Intent(this, StartupActivity::class.java)
            startActivity(intent)
        }
//        if(!isInitialized) {
//            Log.e("start","1")
//            if (App.firebaseAuth == null) {
//                App.firebaseAuth = FirebaseAuth.getInstance()
//            }
//            if (App.firebaseAuth != null && App.firebaseAuth?.currentUser == null) {
//                val intent = Intent(this, AccountActivity::class.java)
//                startActivity(intent)
//            }
//            if (App.firebaseAuth != null && App.firebaseAuth?.currentUser != null){
//                Log.e("User", "Logged")
//                val intent = Intent(this, StartupActivity::class.java)
//                startActivity(intent)
//            }
//
//            isInitialized = true
//        }



    }
}
