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

class password_reset : AppCompatActivity() {
    private var user_email:EditText? = null
    private var reset_btn:Button? = null
    private var firebase:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        user_email = findViewById(R.id.user_email_reset)
        reset_btn = findViewById(R.id.reset_btn)
        firebase = FirebaseAuth.getInstance()

        reset_btn?.setOnClickListener {
            Resetpassword()
        }
    }
    private fun Resetpassword() {
        val email_text = user_email?.text.toString().trim()
        if (TextUtils.isEmpty(email_text)) {
            Toast.makeText(applicationContext, "Please Enter your Emial address to reset the password", Toast.LENGTH_SHORT).show()
        } else {
            firebase?.sendPasswordResetEmail(email_text)?.addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Please check your email to reset the password.", Toast.LENGTH_SHORT).show()
                    } else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}