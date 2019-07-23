package com.example.cse438.blackjack.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.cse438.blackjack.fragment.CreateAccountFragment
import com.example.cse438.blackjack.fragment.SignInFragment

class AccountPagerAdapter(private val context: Context, fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Fragment {
        return if(p0 == 0) {
            CreateAccountFragment(context)
        } else {
            SignInFragment(context)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "Create Account"
        } else {
            "Sign In"
        }
    }
}