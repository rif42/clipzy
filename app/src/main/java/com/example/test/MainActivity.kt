package com.example.test

import android.content.*
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.reflect.Array
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras
        val editclip = bundle?.getString("EXTRA_EDITCLIP")
        val edittimestamp = bundle?.getString("EXTRA_EDITTIMESTAMP")
        val edittimestampid = bundle?.getString("EXTRA_EDITTIMESTAMP_ID")

        val editfield:EditText = findViewById(R.id.main_clipboard)
        val timestampfield:TextView = findViewById(R.id.clip_timestamp)

        editfield.setText(editclip)
        timestampfield.text = edittimestamp

        //navigation menus
        val navhistory:ImageButton=findViewById(R.id.nav_history)
        navhistory.setOnClickListener {
            val intenthistory = Intent(this, ClipboardHistory::class.java)
            startActivity(intenthistory)
        }

        //copypaste menus
        val pastebutton:ImageButton = findViewById(R.id.paste_button)
        pastebutton.setOnClickListener{
            paste()
        }

//        val copybutton:ImageButton = findViewById(R.id.copy_button)
//        copybutton.setOnClickListener {
//            copy()
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        paste()
//    }

    fun paste()
    {
        val mainclip: TextView = findViewById(R.id.main_clipboard)
        val cliptimestamp: TextView = findViewById(R.id.clip_timestamp)
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val testclip = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
        mainclip.text = testclip

        //fetching timestamp
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription.toString()
        val filteredtimestamp = prefilteredcliptimestamp.takeLast(21).dropLast(7)
        cliptimestamp.text = filteredtimestamp
//        clip_timestamp.add(filteredtimestamp)
        //returns 01-05 13:07:19 format

        database = FirebaseDatabase.getInstance().getReference("Clipdata")
        //clip timestamp as key, clip data as value
        //entry example = 01-11 16:09:22: "lorem ipsum dolor sit amet"
        var id = filteredtimestamp
        id = id.filter { it.isDigit() || it.isWhitespace()}

        val entry = dataclip(testclip , filteredtimestamp)
        database.child(id).setValue(entry).addOnSuccessListener {
            Toast.makeText(this, "data saved!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "fail to save data!", Toast.LENGTH_SHORT).show()
        }
    }

//    fun copy()
//    {
//        val copytext:EditText = findViewById(R.id.copytext)
//        val textToCopy = copytext.text
//        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clipData = ClipData.newPlainText("text", textToCopy)
//        clipboardManager.setPrimaryClip(clipData)
//    }
}