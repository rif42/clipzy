package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ClipboardHistoryAdapter(val cliparray: ArrayList<dataclip>) :RecyclerView.Adapter<ClipboardHistoryAdapter.ViewHolder>() {

    private lateinit var database: DatabaseReference
    private lateinit var adaptercontext:Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClipboardHistoryAdapter.ViewHolder {
        adaptercontext = parent.context
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
        var editbtn: ImageButton = itemView.findViewById(R.id.edit_history)
        var copybtn: ImageButton = itemView.findViewById(R.id.copy_history)
        var delbtn: ImageButton = itemView.findViewById(R.id.delete_history)

        init
        {
            itemView.setOnClickListener {
                if (editbtn.isVisible){
                    editbtn.visibility = View.INVISIBLE
                } else {
                    editbtn.visibility = View.VISIBLE
                }

                if (copybtn.isVisible){
                    copybtn.visibility = View.INVISIBLE
                } else {
                    copybtn.visibility = View.VISIBLE
                }

                if (delbtn.isVisible){
                    delbtn.visibility = View.INVISIBLE
                } else {
                    delbtn.visibility = View.VISIBLE
                }
            }

            delbtn.setOnClickListener {
                var tobedeleted = itemTimestamp.text.toString()
                tobedeleted = tobedeleted.filter { it.isDigit() || it.isWhitespace()}
                database = FirebaseDatabase.getInstance().getReference("Clipdata")
                database.child(tobedeleted).removeValue().addOnSuccessListener {
                    Toast.makeText(adaptercontext, "data deleted!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(adaptercontext, "fail to delete data!", Toast.LENGTH_SHORT).show()
                }
            }

            editbtn.setOnClickListener {
                val tobeedited = itemDesc.text.toString()
                val tobeedited_timestamp = itemTimestamp.text.toString()
                val tobeedited_timestamp_id = tobeedited_timestamp.filter { it.isDigit() || it.isWhitespace()}

                val editextras = Bundle()
                editextras.putString("EXTRA_EDITCLIP", tobeedited)
                editextras.putString("EXTRA_EDITTIMESTAMP", tobeedited_timestamp)
                editextras.putString("EXTRA_EDITTIMESTAMP_ID", tobeedited_timestamp_id)

                val editintent = Intent(adaptercontext, MainActivity::class.java)
                editintent.putExtras(editextras)
                startActivity(adaptercontext, editintent, null )
            }
        }

    }
}