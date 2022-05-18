package com.example.taskmanager

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        createTask.setOnClickListener(){
            addTask()
        }

    }

    private fun addTask(){
        val task = hashMapOf("Task" to newTask.text.toString().trim())

        db.collection("newTask").add(task).addOnSuccessListener {
            documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }.addOnFailureListener{
            e -> Log.w(TAG, "Error adding document",e)
        }

        startActivity(Intent(this, MainActivity::class.java))
    }

}