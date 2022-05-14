package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myDBhelper

class AddActivity : AppCompatActivity() {

    companion object {
        lateinit var mydbhelper: myDBhelper
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mydbhelper =myDBhelper(this)
        viewPlans()


        val back=findViewById<ImageView>(R.id.backButton)
        back.setOnClickListener{
            val intent = Intent(applicationContext, ExploreActivity::class.java)
            startActivity(intent)
        }

    }


    private fun viewPlans(){
        val planList: ArrayList<Trip> = mydbhelper.getTrips(this)

        val rView=findViewById<RecyclerView>(R.id.recycler)
        rView.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rView.adapter=addcardRecycler(this,planList)
    }
}