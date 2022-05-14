package com.example.myapplication

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myDBhelper
import java.util.*


class detailsActivity : AppCompatActivity() {

    companion object {
        lateinit var mydbhelper: myDBhelper
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        mydbhelper =myDBhelper(this)


        val window: Window = this@detailsActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@detailsActivity, R.color.detail_statusbar)


        val back=findViewById<ImageView>(R.id.backButton)

        back.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        val s: String = intent.getStringExtra("name").toString()
        val plan: Trip = mydbhelper.getTripDetails(this,s)

        val tripName=findViewById<TextView>(R.id.title)
        val date=findViewById<TextView>(R.id.date)
        val note=findViewById<TextView>(R.id.notes)
        val destinations=findViewById<TextView>(R.id.countries)

        tripName.text=plan.tripNAME
        date.text= convert.convertLongToTime(plan.tripStartDate)+ " - " + convert.convertLongToTime(plan.tripEndDate)
        note.text=plan.tripNote
        destinations.text=plan.destinations

        var cityList= mutableListOf<String>()

        makeList(plan.cities,cityList)
        showCities(cityList)
        //---------------------Making a list of the activities to display---------------------
        var activityList= mutableListOf<String>()
        makeList(plan.activities, activityList)
        showActivities(activityList)

        val delete=findViewById<ImageView>(R.id.deleteicon)
        delete.setOnClickListener(){
            mydbhelper.deletePlan(plan.tripNAME)
            Toast.makeText(this,"Silme islemi basarili.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun makeList(fromDB: String, list: MutableList<String>){
        var i=0
        var city=""
        while(i<fromDB.length) {
            if (fromDB[i]!=';'){
                city+=fromDB[i]
                ++i
            }
            if (fromDB[i]==';') {
                list.add(city)
                city=""
                ++i
            }
        }
    }

    private fun showCities(list: List<String>) {
        var List=list
        val rView = findViewById<RecyclerView>(R.id.CitiesRecyclerView)
        rView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rView.adapter = DetailsRecycler(this, List)
    }

    private fun showActivities(list: List<String>) {
        var List=list
        val rView = findViewById<RecyclerView>(R.id.ActivitiesRecyclerView)
        rView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rView.adapter = DetailsRecycler(this, List)
    }

}
