package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClipboardHistoryAdapter:RecyclerView.Adapter<ClipboardHistoryAdapter.ViewHolder>() {

    private var cliptest = arrayOf("clipboard test one", "clipboard test two", "clipboard test three","clipboard test one", "clipboard test two", "clipboard test three")
    private var cliptesttime = arrayOf("12 Dec 2021, 23:20", "21 Dec 2022, 12:02", "17 Jan 2021, 16:12","12 Dec 2021, 23:20", "21 Dec 2022, 12:02", "17 Jan 2021, 16:12")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClipboardHistoryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ClipboardHistoryAdapter.ViewHolder, position: Int) {
        holder.itemDesc.text = cliptest[position]
        holder.itemTimestamp.text = cliptesttime[position]
    }

    override fun getItemCount(): Int {
        return cliptest.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemDesc: TextView
        var itemTimestamp: TextView

        init {
            itemDesc = itemView.findViewById(R.id.itemDesc)
            itemTimestamp = itemView.findViewById(R.id.itemTimestamp)
        }
    }

}