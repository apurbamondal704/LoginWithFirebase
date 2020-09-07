package com.apurbamondal.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log

class login_activity : AppCompatActivity() {

    private var user_email:EditText? = null
    private var user_password:EditText? = null
    private var login_btn:Button? = null
    private var firebaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)
        user_email = findViewById(R.id.user_email_login)
        user_password = findViewById(R.id.user_password_login)
        login_btn = findViewById(R.id.login_btn_activity)
        firebaseAuth = FirebaseAuth.getInstance()

        login_btn?.setOnClickListener {
            loginUser()
        }
    }
    private fun loginUser() {
        var email_text = user_email?.text.toString().trim()
        var password_text = user_password?.text.toString().trim()

        if (TextUtils.isEmpty(email_text)) {
            Toast.makeText(applicationContext, "Email field can not be empty", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password_text)) {
            Toast.makeText(applicationContext, "You must Enter the password", Toast.LENGTH_SHORT).show()
        } else {
            firebaseAuth?.signInWithEmailAndPassword(email_text, password_text)?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "You are logged in successfully", Toast.LENGTH_SHORT).show()
                        val user:FirebaseUser = firebaseAuth?.currentUser!!
                        if (user.isEmailVerified) {
                            startActivity(Intent(this@login_activity, TestActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Account not verified", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
    public fun Reset(view: View) {
        startActivity(Intent(this@login_activity, password_reset::class.java))
    }
}