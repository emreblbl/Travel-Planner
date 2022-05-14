package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.customListAdapter
import myDBhelper
import java.util.*

class CountdownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countdown_layout)

        val window: Window = this@CountdownActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =
            ContextCompat.getColor(this@CountdownActivity, R.color.countdown_statusbar) //adadad

        val cancel = findViewById<ImageView>(R.id.cancelPlan)
        cancel.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }



        val helper = myDBhelper(applicationContext)
        val db = helper.readableDatabase
        val name = findViewById<TextView>(R.id.tripname)
        val daysRemaining = findViewById<TextView>(R.id.counter)
        val days = findViewById<TextView>(R.id.days)


        val qry = "SELECT * FROM TRIPS"
        val cursor = db.rawQuery(qry, null)

        if (cursor.count != 0) {
            cursor.moveToLast()
            val tripName = cursor.getString(1)
            val tripDate = cursor.getLong(3)
            val currentTime: Long = Calendar.getInstance().timeInMillis
            val difference: Long = tripDate - currentTime
            val days = (((difference / 1000) / 60) / 60) / 24

            name.text = tripName.toString()
            daysRemaining.text = days.toString()
        } else {
            days.visibility = INVISIBLE
            daysRemaining.visibility = INVISIBLE
            name.text = "Aktif Kayit Yok"
        }

        cursor.close()
        db.close()

    }
}