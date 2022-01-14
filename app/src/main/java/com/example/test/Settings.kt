package com.example.test

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.database.*

class Settings: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //navigation menus
        val navhome: ImageButton =findViewById(R.id.nav_home)
        navhome.setOnClickListener {
            val intenthome = Intent(this, MainActivity::class.java)
            startActivity(intenthome)
        }

        val navhistory:ImageButton=findViewById(R.id.nav_history)
        navhistory.setOnClickListener {
            val intenthistory = Intent(this, ClipboardHistory::class.java)
            startActivity(intenthistory)
        }

        //init for font edittexts
        val mcfont:EditText = findViewById(R.id.mcfontsize_value)
        val dbfont:EditText = findViewById(R.id.dbfontsize_value)
        val tsfont:EditText = findViewById(R.id.tsfontsize_value)

        //retrieve font values from firebase
        val fontdb = FirebaseDatabase.getInstance().getReference("Fontsize")
        fontdb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tsfontvalue = snapshot.child("tsfont").getValue() as CharSequence
                val dbfontvalue = snapshot.child("dbfont").getValue() as CharSequence
                val mcfontvalue = snapshot.child("mcfont").getValue() as CharSequence
                tsfont.setText(tsfontvalue)
                dbfont.setText(dbfontvalue)
                mcfont.setText(mcfontvalue)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO()
            }
        })

        //switch for offline data syncing
        val offlinedbswitch: SwitchCompat = findViewById(R.id.dbswitch)
        offlinedbswitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                val database = FirebaseDatabase.getInstance().getReference("Clipdata")
                database.keepSynced(true)
                Toast.makeText(this, "database will sync automatically!", Toast.LENGTH_SHORT).show()
            } else
            {
                val database = FirebaseDatabase.getInstance().getReference("Clipdata")
                database.keepSynced(false)
                Toast.makeText(this, "database syncing disabled!", Toast.LENGTH_SHORT).show()
            }
        }

        //edits font values on main clip(mainactivity), database items, and its timestamp on the database
        mcfont.doAfterTextChanged {
            val mcfont_value = mcfont.text.toString()
            val database = FirebaseDatabase.getInstance().getReference("Fontsize")
            database.child("mcfont").setValue(mcfont_value).addOnSuccessListener {
//                Toast.makeText(this, "main clip font changed!", Toast.LENGTH_SHORT).show()
            }
        }

        dbfont.doAfterTextChanged {
            val dbfont_value = dbfont.text.toString()
            val database = FirebaseDatabase.getInstance().getReference("Fontsize")
            database.child("dbfont").setValue(dbfont_value).addOnSuccessListener {
//                Toast.makeText(this, "database clip font changed!", Toast.LENGTH_SHORT).show()
            }
        }

        tsfont.doAfterTextChanged {
            val tsfont_value = tsfont.text.toString()
            val database = FirebaseDatabase.getInstance().getReference("Fontsize")
            database.child("tsfont").setValue(tsfont_value).addOnSuccessListener {
//                Toast.makeText(this, "timestamp clip font changed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
