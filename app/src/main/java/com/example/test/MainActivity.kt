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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        val navbutton:ImageButton=findViewById(R.id.navigation_history)
//        navbutton.setOnClickListener {
//            val intent = Intent(this, ClipboardHistory::class.java)
//            startActivity(intent)
//        }

        val pastebutton:ImageButton = findViewById(R.id.paste_button)
        pastebutton.setOnClickListener{
            paste()
        }

        val copybutton:ImageButton = findViewById(R.id.copy_button)
        copybutton.setOnClickListener {
            copy()
        }
    }


    override fun onResume() {
        super.onResume()
        paste()
    }

    fun paste()
    {
        val mainclip:TextView = findViewById(R.id.main_clipboard)
        val editclip: EditText = findViewById(R.id.copytext)
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mainclip.text = clipboardManager.primaryClip?.getItemAt(0)?.text

        Toast.makeText(this, "Text Pasted", Toast.LENGTH_SHORT).show()
    }

    fun copy()
    {
        val copytext:EditText = findViewById(R.id.copytext)
        val textToCopy = copytext.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
    }
}




