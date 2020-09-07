package com.apurbamondal.loginwithfirebase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*

class Firebase_storage : AppCompatActivity() {
    private var upload_btn: Button? = null
    private var image_view:ImageView? = null
    private var imageUri: Uri? = null
    private var storage:FirebaseStorage? = null
    private var imageRef:StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_storage)
        upload_btn = findViewById(R.id.upload_image_btn)
        image_view = findViewById(R.id.imageview)
        storage = FirebaseStorage.getInstance()
        imageRef = storage?.reference?.child("Images")

        image_view?.setOnClickListener {
            PickImageFromGallery()
        }

        upload_btn?.setOnClickListener {
            UploadImageToTheFirebase()
        }
    }
    private fun PickImageFromGallery() {
        val Gallery = Intent()
        Gallery.type = "image/*"
        Gallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Gallery, 111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            try {
                val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                image_view?.setImageBitmap(image)
            } catch (error:IOException) {
                Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun UploadImageToTheFirebase() {
        if (imageUri != null) {
            val ref = imageRef?.child(UUID.randomUUID().toString())
            ref?.putFile(imageUri!!)?.addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Image added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}