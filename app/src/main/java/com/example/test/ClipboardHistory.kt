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

        val navsetting:ImageButton=findViewById(R.id.nav_settings)
        navsetting.setOnClickListener {
            val intentsettings = Intent(this, Settings::class.java)
            startActivity(intentsettings)
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

        //fetching timestamp
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription.toString()
        val filteredtimestamp = prefilteredcliptimestamp.takeLast(21).dropLast(7)
        //returns 01-05 13:07:19 format

        //save clip and timestamp on firebase
        database = FirebaseDatabase.getInstance().getReference("Clipdata")

        var id = filteredtimestamp
        id = id.filter { it.isDigit() || it.isWhitespace()}

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
}