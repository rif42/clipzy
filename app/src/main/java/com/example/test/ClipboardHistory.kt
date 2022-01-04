package com.example.test

import android.os.Bundle
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

        val cliphistoryrecview: RecyclerView = findViewById(R.id.cliphistoryrecview)

        layoutManager = LinearLayoutManager(this)

        cliphistoryrecview.layoutManager = layoutManager

        adapter = ClipboardHistoryAdapter()
        cliphistoryrecview.adapter = adapter
    }
}