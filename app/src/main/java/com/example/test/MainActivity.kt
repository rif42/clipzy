package com.example.test

import android.content.*
import android.graphics.Paint
import android.graphics.Typeface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
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

        var bundle = intent.extras
        val editclip = bundle?.getString("EXTRA_EDITCLIP")
        val edittimestamp = bundle?.getString("EXTRA_EDITTIMESTAMP")
        val edittimestampid = bundle?.getString("EXTRA_EDITTIMESTAMP_ID")

        val editfield:EditText = findViewById(R.id.main_clipboard)
        val timestampfield:TextView = findViewById(R.id.clip_timestamp)

        editfield.setText(editclip)
        timestampfield.text = edittimestamp

        //edit menu (confirm/reject)
        val okbutton: ImageButton = findViewById(R.id.confirm_button)
        if (bundle == null)
        {
            okbutton.visibility = View.GONE
        } else
        {
            okbutton.visibility = View.VISIBLE
        }
        okbutton.setOnClickListener {
            val editvalue = editfield.text.toString()
            val editdata = mapOf<String,String>(
                "data" to editvalue
            )
            database = FirebaseDatabase.getInstance().getReference("Clipdata")
            database.child(edittimestampid!!).updateChildren(editdata).addOnSuccessListener {
                editfield.text.clear()
                Toast.makeText(this, "data edited!", Toast.LENGTH_SHORT).show()
                val intenthistory = Intent(this, ClipboardHistory::class.java)
                bundle = null
                startActivity(intenthistory)
            }
        }

        val delbutton: ImageButton = findViewById(R.id.reject_button)
        if (bundle == null)
        {
            delbutton.visibility = View.GONE
        } else
        {
            delbutton.visibility = View.VISIBLE
        }

        delbutton.setOnClickListener {
            editfield.text.clear()
            val intenthistory = Intent(this, ClipboardHistory::class.java)
            bundle = null
            startActivity(intenthistory)
        }

        //navigation menus
        val navhistory:ImageButton=findViewById(R.id.nav_history)
        navhistory.setOnClickListener {
            val intenthistory = Intent(this, ClipboardHistory::class.java)
            startActivity(intenthistory)
        }

        //text modifier menus
        val bold:ImageButton = findViewById(R.id.bold_button)
        bold.setOnClickListener {
            val spannableString: Spannable = SpannableStringBuilder(editfield.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                editfield.selectionStart,
                editfield.selectionEnd,
                0
            )
            editfield.setText(spannableString)
        }

        val italic:ImageButton = findViewById(R.id.italic_button)
        italic.setOnClickListener {
            val spannableString: Spannable = SpannableStringBuilder(editfield.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                editfield.selectionStart,
                editfield.selectionEnd,
                0
            )
            editfield.setText(spannableString)
        }

        val underline:ImageButton = findViewById(R.id.underline_button)
        underline.setOnClickListener {
            val spannableString: Spannable = SpannableStringBuilder(editfield.text)
            spannableString.setSpan(
                UnderlineSpan(),
                editfield.selectionStart,
                editfield.selectionEnd,
                0
            )
            editfield.setText(spannableString)
        }

        val strike:ImageButton = findViewById(R.id.strikethrough_button)
        strike.setOnClickListener {
            val spannableString: Spannable = SpannableStringBuilder(editfield.text)
            spannableString.setSpan(
                StrikethroughSpan(),
                editfield.selectionStart,
                editfield.selectionEnd,
                0
            )
            editfield.setText(spannableString)
        }
    }
}