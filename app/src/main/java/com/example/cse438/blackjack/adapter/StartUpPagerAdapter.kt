package com.example.cse438.blackjack.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.cse438.blackjack.fragment.*

class StartUpPagerAdapter (private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(p0: Int): Fragment {
        return if(p0 == 0) {
            ViewStatusFragment(context)
        } else if(p0 == 1){
            ViewRankFragment(context)
        } else {
            ViewRecordFragment(context)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "Status"
        } else if(position == 1){
            "Rank"
        } else {
            "Record"
        }
    }
}