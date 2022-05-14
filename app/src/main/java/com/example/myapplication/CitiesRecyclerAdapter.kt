package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class CitiesRecyclerAdapter(myCtx: Context, val items: List<ActivitiesClass>): RecyclerView.Adapter<CitiesRecyclerAdapter.ViewHolder>() {

    val context=myCtx
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.itemname)
        var addButton: TextView=itemView.findViewById(R.id.addToPlan)
        var itemImage: ImageView=itemView.findViewById(R.id.itemimage)
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
            adding.city=holder.itemTitle.text.toString()
            startActivity(cntx,intent, Bundle())
        }
        holder.itemImage.setOnClickListener {
            val cntx=holder.addButton.context
            val intent = Intent(cntx, LocationActivity::class.java)
            intent.putExtra("sehir",items[position].activityName)
            startActivity(cntx,intent, Bundle())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}