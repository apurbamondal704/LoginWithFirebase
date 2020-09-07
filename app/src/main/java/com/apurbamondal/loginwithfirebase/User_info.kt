package com.apurbamondal.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class User_info : AppCompatActivity() {
    private var firstName:TextView? = null
    private var lastName:TextView? = null
    private var userName: TextView? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var firebaseDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        firstName = findViewById(R.id.firstName_textview)
        lastName = findViewById(R.id.lastName_textview)
        userName = findViewById(R.id.userName_textview)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        firebaseDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(task: DataSnapshot) {
                if (task.exists()) {
                    val firstname = task.child("firstName").value as String
                    val lastname = task.child("lastName").value as String
                    val username = task.child("userName").value as String
                    firstName?.text = firstname
                    lastName?.text = lastname
                    userName?.text = username
                }
            }
        })

    }

    public fun UpdateInfo(view: View) {
        startActivity(Intent(this@User_info, Profile_activity::class.java))
    }
    public fun ChangeEmail(view: View) {
        startActivity(Intent(this@User_info, changeEmail::class.java))
    }
}