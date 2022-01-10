package com.example.test

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClipboardHistory : AppCompatActivity() {

    private var adaptercliplist = mutableSetOf<String>("test")
    private var adaptercliptime = mutableSetOf<String>("testdata")

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<ClipboardHistoryAdapter.ViewHolder>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clipboard_history)

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
        adapter = ClipboardHistoryAdapter(adaptercliplist, adaptercliptime)
        cliphistoryrecview.adapter = adapter

        val pastebutton:ImageButton = findViewById(R.id.paste_button)
        pastebutton.setOnClickListener{
            paste()
            val sharedPrefClip: SharedPreferences = getSharedPreferences("clipsharedpref", MODE_PRIVATE)
            val clipeditor = sharedPrefClip.edit()
            clipeditor.apply {
                putStringSet("clipdata", adaptercliplist)
                apply()
            }

            val sharedPrefTime: SharedPreferences = getSharedPreferences("timesharedpref", MODE_PRIVATE)
            val timeeditor = sharedPrefTime.edit()
            timeeditor.apply {
                putStringSet("cliptime", adaptercliptime)
                apply()
            }

            //assigns new clipboard to new list
            adaptercliplist = sharedPrefClip.getStringSet("clipsharedpref", adaptercliplist) as MutableSet<String>
            adaptercliptime = sharedPrefTime.getStringSet("timesharedpref", adaptercliptime) as MutableSet<String>

            //refreshes the adapter so new data get loaded
            adapter = ClipboardHistoryAdapter(adaptercliplist, adaptercliptime)
            cliphistoryrecview.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        //refreshes the adapter so new data get loaded
        val cliphistoryrecview: RecyclerView = findViewById(R.id.cliphistoryrecview)
        layoutManager = LinearLayoutManager(this)
        cliphistoryrecview.layoutManager = layoutManager
        adapter = ClipboardHistoryAdapter(adaptercliplist, adaptercliptime)
        cliphistoryrecview.adapter = adapter
    }

    fun paste()
    {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var mainclip = clipboardManager.primaryClip?.getItemAt(0)?.text

        //adding clip to array for history tracking
        adaptercliplist.add(mainclip.toString())

        //fetching timestamp
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription.toString()
        val filteredtimestamp = prefilteredcliptimestamp.takeLast(21)?.dropLast(7)
        adaptercliptime.add(filteredtimestamp)
        //returns 01-05 13:07:19 format

        Toast.makeText(this, adaptercliplist.toString(), Toast.LENGTH_SHORT).show()
    }
}