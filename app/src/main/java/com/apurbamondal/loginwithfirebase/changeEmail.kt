package com.apurbamondal.loginwithfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class changeEmail : AppCompatActivity() {

    private var user_email:EditText? = null
    private var user_password:EditText? = null
    private var user_newemail:EditText? = null
    private var update:Button? = null
    private var firebaseauth:FirebaseAuth? = null
    private var user:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        user_email = findViewById(R.id.email_edit_text_change)
        user_password = findViewById(R.id.password_edit_text_change)
        user_newemail = findViewById(R.id.newemail_edit_text_change)
        update = findViewById(R.id.update_btn)
        firebaseauth = FirebaseAuth.getInstance()
        user = firebaseauth?.currentUser

        update?.setOnClickListener {
            updateEmail()
        }
    }
    private fun updateEmail() {
        val email = user_email?.text.toString().trim()
        val password = user_password?.text.toString().trim()
        val newemail = user_newemail?.text.toString().trim()

        val userinfo = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(userinfo)?.addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(task: Task<Void>) {
                user!!.updateEmail(newemail).addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(task1: Task<Void>) {
                        if (task1.isSuccessful) {
                            Toast.makeText(applicationContext, "Your Email has been Updated.", Toast.LENGTH_SHORT).show()
                        } else {
                            val error = task1.exception?.message
                            Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
    }
}