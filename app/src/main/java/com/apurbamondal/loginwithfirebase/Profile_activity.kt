package com.apurbamondal.loginwithfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Profile_activity : AppCompatActivity() {
    private var user_firstname:EditText? = null
    private var user_lastname:EditText? = null
    private var username:EditText? = null
    private var update:Button? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var firebaseDatabase:DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_activity)
        user_firstname = findViewById(R.id.user_first_name)
        user_lastname = findViewById(R.id.user_last_name)
        username = findViewById(R.id.user_username)
        update = findViewById(R.id.userinfo_update_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        update?.setOnClickListener {
            SaveUserInfo()
        }
    }
    private fun SaveUserInfo() {
        val firstname = user_firstname?.text.toString().trim()
        val lastname = user_lastname?.text.toString().trim()
        val username = username?.text.toString().trim()
        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(applicationContext, "Please fill your First Name", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(applicationContext, "PLease fill your last name ", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(applicationContext, "PLese something in username field", Toast.LENGTH_SHORT).show()
        } else {
            val userinfo = HashMap<String, Any>()
            userinfo.put("firstName", firstname)
            userinfo.put("lastName", lastname)
            userinfo.put("userName", username)
            firebaseDatabase?.updateChildren(userinfo)?.addOnCompleteListener(object :OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Your Information is Updated", Toast.LENGTH_SHORT).show()
                    } else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}