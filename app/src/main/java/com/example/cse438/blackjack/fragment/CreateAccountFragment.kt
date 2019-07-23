package com.example.cse438.blackjack.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cse438.blackjack.App
import com.example.cse438.blackjack.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_account.*

@SuppressLint("ValidFragment")
class CreateAccountFragment(context: Context):Fragment() {
    private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onStart() {
        super.onStart()

        create_account.setOnClickListener{
            val username = username_cre_tv.text.toString()
            val password = password_cre_tv.text.toString()
            val email = email_cre_tv.text.toString()


            if(username != ""  && password != "" && email != "") {
                App.firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener{it2 ->
                    if(it2.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()

                        val userData = HashMap<String, Any>()
                        userData["username"] = username
                        userData["email"] = email
                        userData["win"] = 0
                        userData["lose"] = 0
                        userData["score"] = 0
                        userData["game_time"] = 0
                        userData["win_rate"] = 0

                        db.document("users/${App.firebaseAuth?.currentUser?.uid}")
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(parentContext, "Success to write user data", Toast.LENGTH_SHORT).show()
                                activity?.finish()
                            }
                            .addOnFailureListener{
                                Toast.makeText(parentContext, "Failed to write user data", Toast.LENGTH_SHORT).show()
                            }
                    }else {
                        Toast.makeText(parentContext, "Email and/or password unacceptable", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(parentContext, "Must fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}