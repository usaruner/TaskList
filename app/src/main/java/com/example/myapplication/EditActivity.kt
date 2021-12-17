package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.app.Activity

import android.content.Intent




class EditActivity : AppCompatActivity() {
    lateinit var  adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        var message = intent.getStringExtra("tasks")
        var position = intent.getIntExtra("position",0)
        var edtxt = findViewById<EditText>(R.id.editTextTextPersonName2)
        edtxt.setText(message)
        val btn = findViewById<Button>(R.id.button2)
        btn.setOnClickListener{
            val str = edtxt.text.toString()
            Log.d( "send",str)
            Log.d( "pos",position.toString())
            val returnIntent = Intent(this, MainActivity::class.java)
            returnIntent.putExtra("result", str)
            returnIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}