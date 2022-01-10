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
import java.lang.reflect.Array


class MainActivity : AppCompatActivity() {

    private var clip_current = mutableSetOf<String>("")
    private var clip_timestamp = mutableSetOf<String>("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
//            val sharedPref: SharedPreferences = getSharedPreferences("clipsharedpref", MODE_PRIVATE)
//            val editor = sharedPref.edit()
//
//            editor.apply {
//                putStringSet("clipdata", clip_current)
//                putStringSet("cliptimestamp", clip_timestamp)
//                apply()
//            }
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
        mainclip.text = clipboardManager.primaryClip?.getItemAt(0)?.text

        //adding clip to array for history tracking
//        clip_current.add(mainclip.text.toString())
//        cliplist.add(currentclip)

        //fetching timestamp
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription.toString()
        val filteredtimestamp = prefilteredcliptimestamp.takeLast(21)?.dropLast(7)
        cliptimestamp.text = filteredtimestamp
//        clip_timestamp.add(filteredtimestamp)
        //returns 01-05 13:07:19 format

        //adding clip timestamp to array for timestamp tracking
//        if (filteredtimestamp != null) {
//            cliptime.add(filteredtimestamp)
//        }

//        //passing data to clipboardhistory using intent, replacing it with sharedpreference
//        Intent(this, ClipboardHistory::class.java).also{
//            it.putExtra("EXTRA_CLIP", currentclip)
//            it.putExtra("EXTRA_TIMESTAMP", filteredtimestamp)
//            startActivity(it)
//        };

        Toast.makeText(this, "Text Pasted", Toast.LENGTH_SHORT).show()
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




