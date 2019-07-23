package com.example.cse438.blackjack.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.adapter.AccountPagerAdapter
import kotlinx.android.synthetic.main.activity_account.*



class AccountActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val adapter = AccountPagerAdapter(this, supportFragmentManager)

        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
    }

//    override fun onBackPressed() {
//        finishAffinity()
//    }
}
