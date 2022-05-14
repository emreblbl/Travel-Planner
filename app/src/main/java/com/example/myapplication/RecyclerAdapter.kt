package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity


class RecyclerAdapter(myCtx: Context, val trips: ArrayList<Trip>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.itemname)
        var itemDest: TextView = itemView.findViewById(R.id.destinaton)
        var itemStart: TextView = itemView.findViewById(R.id.date)
        var itemEdit: ImageView= itemView.findViewById(R.id.editIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val trip: Trip=trips[position]
        holder.itemTitle.text=trip.tripNAME
        holder.itemDest.text="to " + getMainDestination(trip.destinations)
        holder.itemStart.text= convertLongToTime(trip.tripStartDate)

        holder.itemView.setOnClickListener(){
            val ctx=holder.itemTitle.context
            val intent = Intent(ctx, detailsActivity::class.java)
            intent.putExtra("name", holder.itemTitle.text)
            startActivity(ctx,intent,Bundle())
        }
        holder.itemEdit.setOnClickListener(){
            val ctx=holder.itemTitle.context
            val intent = Intent(ctx, EditActivity::class.java)
            intent.putExtra("name", holder.itemTitle.text)
            startActivity(ctx,intent,Bundle())
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

    private fun getMainDestination(destinations: String) : String {
        var main=""
        var i=0
        while (i<destinations.length){
            if (destinations[i]!=',') {
                main += destinations[i]
                ++i
            }
            else break
        }
        return main
    }

}