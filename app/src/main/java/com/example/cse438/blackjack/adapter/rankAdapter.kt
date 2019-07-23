package com.example.cse438.blackjack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cse438.blackjack.model.User

class rankAdapter (context: Context, resource:Int, arr:Array<User>):ArrayAdapter<User>(context, resource,arr){
    private var parentContext:Context
    private var foodArray: Array<User>
    private var vi : LayoutInflater

    init{
        this.parentContext = context
        this.foodArray  = arr
        this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

}