package com.example.test

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {

    val cliplist:MutableList<CharSequence> = arrayListOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //appending stuff into the array


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

    override fun onResume() {
        super.onResume()
        paste()
    }

    fun paste()
    {
        val mainclip: TextView = findViewById(R.id.main_clipboard)
        val cliptimestamp: TextView = findViewById(R.id.clip_timestamp)
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mainclip.text = clipboardManager.primaryClip?.getItemAt(0)?.text
        val prefilteredcliptimestamp = clipboardManager.primaryClipDescription?.toString()


        //01-05 13:07:19.782

        //adding clip to array for history tracking
        val currentclip = mainclip.text
        cliplist.add(currentclip)

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




