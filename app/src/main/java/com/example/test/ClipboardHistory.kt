package com.example.test

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClipboardHistory : AppCompatActivity() {
    val cliplist:MutableList<String?> = arrayListOf("")
    val cliptime:MutableList<String?> = arrayListOf("")

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<ClipboardHistoryAdapter.ViewHolder>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clipboard_history)

        val sharedPref: SharedPreferences = getSharedPreferences("clipsharedpref", MODE_PRIVATE)
        val clip_data = sharedPref.getString("clipdata", null)
        val clip_timestamp = sharedPref.getString("cliptimestamp", null)

        cliplist.add(clip_data)
        cliptime.add(clip_timestamp)

        //navigation menu
        val navhome: ImageButton =findViewById(R.id.nav_home)
        navhome.setOnClickListener {
            val intenthome = Intent(this, MainActivity::class.java)
            startActivity(intenthome)
        }

        //for recyclerview
        val cliphistoryrecview: RecyclerView = findViewById(R.id.cliphistoryrecview)

        layoutManager = LinearLayoutManager(this)

        cliphistoryrecview.layoutManager = layoutManager

        adapter = ClipboardHistoryAdapter(cliplist, cliptime)
        cliphistoryrecview.adapter = adapter

    }
}