package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ActivityClipboardHistoryBinding

class ClipboardHistory : AppCompatActivity() {

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

        adapter = ClipboardHistoryAdapter()
        cliphistoryrecview.adapter = adapter
    }
}