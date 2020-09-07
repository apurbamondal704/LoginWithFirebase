package com.apurbamondal.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
    public fun ChangeEmail(view: View) {
        startActivity(Intent(this@TestActivity, changeEmail::class.java))
    }
    public fun UpdateInformation(view: View) {
        startActivity(Intent(this@TestActivity, Profile_activity::class.java))
    }
    public fun Information(view: View) {
        startActivity(Intent(this@TestActivity, User_info::class.java))
    }
    public fun uploadImage(view: View) {
        startActivity(Intent(this@TestActivity, Firebase_storage::class.java))
    }
}