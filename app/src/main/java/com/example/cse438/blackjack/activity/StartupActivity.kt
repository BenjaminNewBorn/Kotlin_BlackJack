package com.example.cse438.blackjack.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cse438.blackjack.R
import com.example.cse438.blackjack.adapter.StartUpPagerAdapter
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_startup.*

class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val adapter = StartUpPagerAdapter(this, supportFragmentManager)

        startup_viewpager.adapter = adapter
        startup_tabs.setupWithViewPager(startup_viewpager)
    }
//
//    override fun onBackPressed() {
//        finishAffinity()
//    }
}
