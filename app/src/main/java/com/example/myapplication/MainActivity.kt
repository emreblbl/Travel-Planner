package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myDBhelper


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var mydbhelper: myDBhelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mydbhelper =myDBhelper(this)
        viewPlans()
        adding.setDefault()

        val startPlan = findViewById<ImageView>(R.id.button_newtrip)
        val explore=findViewById<ImageView>(R.id.explore_button)
        val countdown=findViewById<ImageView>(R.id.button_countdown)

        startPlan.setOnClickListener() {
            val intent = Intent(applicationContext, PlanningActivity::class.java)
            startActivity(intent)
        }
        countdown.setOnClickListener(){
            val intent = Intent(applicationContext, CountdownActivity::class.java)
            startActivity(intent)
        }

        explore.setOnClickListener(){
            val intent = Intent(applicationContext, ExploreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun viewPlans(){
        val planList: ArrayList<Trip> = mydbhelper.getTrips(this)
        val rView=findViewById<RecyclerView>(R.id.recycler)
        rView.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rView.adapter=RecyclerAdapter(this,planList)
    }
}