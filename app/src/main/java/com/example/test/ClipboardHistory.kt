package com.example.test

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.logging.Logger
import kotlin.random.Random

class ClipboardHistory : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var cliphistoryrecview: RecyclerView
    private lateinit var cliparray: ArrayList<dataclip>

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
        cliphistoryrecview = findViewById(R.id.cliphistoryrecview)
        cliphistoryrecview.layoutManager = LinearLayoutManager(this)
        cliphistoryrecview.setHasFixedSize(true)
        getUserData()

        val pastebutton:ImageButton = findViewById(R.id.paste_button)
        pastebutton.setOnClickListener{
            paste()
        }
    }

    fun paste()
    {
        //fetching clipboard data
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mainclip = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()

        val id = Random.nextInt(1000, 9999).toString()

        //fetching timestamp
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription.toString()
        val filteredtimestamp = prefilteredcliptimestamp.takeLast(21).dropLast(7)
        //returns 01-05 13:07:19 format

        //save clip and timestamp on firebase
        database = FirebaseDatabase.getInstance().getReference("Clipdata")
        //clip timestamp as key, clip data as value
        //entry example = 01-11 16:09:22: "lorem ipsum dolor sit amet"

        val entry = dataclip(mainclip , filteredtimestamp)
        database.child(id).setValue(entry).addOnSuccessListener {
            Toast.makeText(this, "data saved!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "fail to save data!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserData()
    {
        database = FirebaseDatabase.getInstance().getReference("Clipdata")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                cliparray = arrayListOf<dataclip>()
                if (snapshot.exists())
                {
                    for (userSnapshot in snapshot.children)
                    {
                        val datavalue = userSnapshot.getValue(dataclip::class.java)
                        cliparray.add(datavalue!!)
                    }
                    cliphistoryrecview.adapter = ClipboardHistoryAdapter(cliparray)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    fun update()
//    {
//        val sharedPrefClip: SharedPreferences = getSharedPreferences("clipsharedpref", MODE_PRIVATE)
//        val clipeditor = sharedPrefClip.edit()
//        clipeditor.apply {
//            putStringSet("clipdata", adaptercliplist)
//            apply()
//        }
//
//        val sharedPrefTime: SharedPreferences = getSharedPreferences("timesharedpref", MODE_PRIVATE)
//        val timeeditor = sharedPrefTime.edit()
//        timeeditor.apply {
//            putStringSet("cliptime", adaptercliptime)
//            apply()
//        }
//
//        //assigns new clipboard to new list
//        adaptercliplist = sharedPrefClip.getStringSet("clipsharedpref", adaptercliplist) as MutableSet<String>
//        adaptercliptime = sharedPrefTime.getStringSet("timesharedpref", adaptercliptime) as MutableSet<String>
//
//        //refreshes the adapter so new data get loaded
//        val cliphistoryrecview: RecyclerView = findViewById(R.id.cliphistoryrecview)
//        adapter = ClipboardHistoryAdapter(adaptercliplist, adaptercliptime)
//        cliphistoryrecview.adapter = adapter
//    }
}