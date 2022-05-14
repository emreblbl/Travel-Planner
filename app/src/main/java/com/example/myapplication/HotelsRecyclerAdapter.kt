package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HotelsRecyclerAdapter(myCtx: Context, val items: List<String>): RecyclerView.Adapter<HotelsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.itemname)
        var addButton: TextView=itemView.findViewById(R.id.addToPlan)
        var itemImage: ImageView=itemView.findViewById(R.id.itemimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.hotels_recycler_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text=items[position]
        val ctx=holder.itemTitle.context
        holder.itemImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.hotels))
        holder.itemImage.setOnClickListener {
            val cntx=holder.addButton.context
            val intent = Intent(cntx, LocationActivity::class.java)
            intent.putExtra("city",items[position])
            ContextCompat.startActivity(cntx, intent, Bundle())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}