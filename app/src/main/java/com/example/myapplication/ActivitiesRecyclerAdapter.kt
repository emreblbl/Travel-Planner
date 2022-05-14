package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import androidx.core.content.ContextCompat

class ActivitiesRecyclerAdapter(myCtx: Context, val items: List<ActivitiesClass>): RecyclerView.Adapter<ActivitiesRecyclerAdapter.ViewHolder>() {

    val context=myCtx
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.itemname)
        var addButton: TextView=itemView.findViewById(R.id.addToPlan)
        var itemImage: ImageView =itemView.findViewById(R.id.itemimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.cities_recycler_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text=items[position].activityName
        holder.itemImage.setImageDrawable(ContextCompat.getDrawable(context, items[position].activityImage))
        holder.addButton.setOnClickListener {
            val cntx=holder.addButton.context
            val intent = Intent(cntx, AddActivity::class.java)
            adding.activity=holder.itemTitle.text.toString()
            ContextCompat.startActivity(cntx, intent, Bundle())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}