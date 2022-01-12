package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClipboardHistoryAdapter(val cliparray: ArrayList<dataclip>) :RecyclerView.Adapter<ClipboardHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClipboardHistoryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ClipboardHistoryAdapter.ViewHolder, position: Int) {
        val currentitem = cliparray[position]
        holder.itemDesc.text = currentitem.data
        holder.itemTimestamp.text = currentitem.timestamp
    }

    override fun getItemCount(): Int {
        return cliparray.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemDesc: TextView = itemView.findViewById(R.id.itemDesc)
        var itemTimestamp: TextView = itemView.findViewById(R.id.itemTimestamp)
    }
}