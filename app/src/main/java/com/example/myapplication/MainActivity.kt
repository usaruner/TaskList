package com.example.myapplication

import android.R.attr
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import android.R.attr.data
import android.R.attr.value
import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback


class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var  adapter : TaskItemAdapter
    var res = "";
    val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result: ActivityResult? ->
        if (result != null) {
            if(result.resultCode == RESULT_OK){
                res = result.data!!.getStringExtra("result").toString()
                var position = result.data!!.getIntExtra("position",0)
                listOfTasks[position] = res
                adapter.notifyDataSetChanged()
                saveItems()
                Log.d( "res",res)
                Log.d( "pos", position.toString())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        val onLongClickListener = object :TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }
        val onClickListener = object :TaskItemAdapter.OnClickListener{
            val intent = Intent(this@MainActivity,EditActivity::class.java)
            override fun onItemClicked(position: Int) {
                var array = arrayListOf<String>()
                array.addAll(listOfTasks)
                intent.putExtra("tasks", listOfTasks[position].toString())
                intent.putExtra("position", position)
                getResult.launch(intent)
//                startActivity(intent)
                res = intent.getStringExtra("result").toString()

                Log.d("DEBUG ", "Value: $position")
            }
        }
        loadItems()
        var recycle = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener,onClickListener)
        val userinput = findViewById<EditText>(R.id.editTextTextPersonName)
        recycle.adapter = adapter
        recycle.layoutManager = LinearLayoutManager(this)
        btn.setOnClickListener{
            listOfTasks.add(userinput.text.toString())
            adapter.notifyItemInserted(listOfTasks.size -1)
            userinput.setText("")

        }
        startActivity(intent)
    }

    fun getDatafile(): File{
        return File(filesDir,"data.txt")
    }
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDatafile(), Charset.defaultCharset())
        } catch(ioException:IOException){
            ioException.printStackTrace()
        }
    }
    fun saveItems(){
        try{
            FileUtils.writeLines(getDatafile(),listOfTasks)
        } catch(ioException:IOException){
            ioException.printStackTrace()
        }
    }

}