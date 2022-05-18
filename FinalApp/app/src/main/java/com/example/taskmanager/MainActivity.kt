package com.example.taskmanager

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readFireStoreData()

        removeEventBtn.setOnClickListener{
            var taskListView = findViewById<ListView>(R.id.listView)
            var taskList = arrayListOf<String>()
            var adapter: ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice,taskList)
            val db = FirebaseFirestore.getInstance()

            val position: SparseBooleanArray = taskListView.checkedItemPositions
            val count = taskListView.count
            var item = count -1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(taskList.get(item))
                    db.collection("newTask").document()
                        .delete()
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }

                item--

            }
            position.clear()
            adapter.notifyDataSetChanged()
        }


    }


    fun readFireStoreData(){
        var taskListView = findViewById<ListView>(R.id.listView)
        var taskList = arrayListOf<String>()
        var adapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice,taskList)

        val db = FirebaseFirestore.getInstance()

        db.collection("newTask").get()
            .addOnCompleteListener{
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for (document in it.result!!){
                        result.append(document.data.getValue("Task")).append("    ")
                    }
                    taskList.add(result.toString())
                    taskListView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
    }


    fun goToCreateTask(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))

    }

    fun goToPreferences(view: View){
        startActivity(Intent(this,SettingsActivity::class.java))
    }

    fun goToHelp(view: View){
        startActivity(Intent(this, HelpActivity::class.java))
    }
}