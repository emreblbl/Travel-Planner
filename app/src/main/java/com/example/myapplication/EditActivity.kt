package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_planning.*
import kotlinx.android.synthetic.main.activity_planning.selectedDate
import myDBhelper
import java.util.ArrayList

var editStartDate: Long = 0
var editEndDate: Long = 0

class EditActivity : AppCompatActivity() {

    companion object {
        lateinit var mydbhelper: myDBhelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        mydbhelper =myDBhelper(this)


        val window: Window = this@EditActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@EditActivity, R.color.detail_statusbar)


        val back=findViewById<ImageView>(R.id.backButton)

        back.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        val selectDate=findViewById<ImageView>(R.id.selectDate)

        selectDate.setOnClickListener {
            buildDatePicker()
        }

        val spinner1=findViewById<Spinner>(R.id.dropdownlist1)
        val spinner2=findViewById<Spinner>(R.id.dropdownlist2)
        val spinner3=findViewById<Spinner>(R.id.dropdownlist3)

        val addDestination1=findViewById<ImageView>(R.id.add_destination1)
        val addDestination2=findViewById<ImageView>(R.id.add_destination2)

        addDestination1.setOnClickListener {
            addDestination1.visibility= View.GONE
            spinner2.visibility=View.VISIBLE
            addDestination2.visibility=View.VISIBLE
        }

        addDestination2.setOnClickListener {
            addDestination2.visibility=View.GONE
            spinner3.visibility=View.VISIBLE
        }


        val items= arrayOf("Ulke secilmedi.","Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia","Australia","Austria","Azerbaijan","The Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Central African Republic","Chad","Chile","China","Colombia","Comoros","Congo","Costa Rica","Cote d'Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor (Timor-Leste)","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Fiji","Finland","France","Gabon","The Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, South","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar (Burma)","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe")
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter


        val s: String = intent.getStringExtra("name").toString()
        val plan: Trip = detailsActivity.mydbhelper.getTripDetails(this,s)

        val tripName=findViewById<TextView>(R.id.title)
        val tripDate=findViewById<TextView>(R.id.selectedDate)
        val note=findViewById<TextView>(R.id.notes)

        tripName.text=plan.tripNAME
        tripDate.text= convert.convertLongToTime(plan.tripStartDate)+ " - " + convert.convertLongToTime(plan.tripEndDate)
        note.text=plan.tripNote

        //------------------------------Setting default for spinners---------------------------
        var i=0
        var country=""
        var destNum=1
        val destinations= mutableListOf<String>()
        while (i<plan.destinations.length){
            if(plan.destinations[i]!=',' && i!=plan.destinations.length-1) {
                country+= plan.destinations[i]
            }
            else if (i==plan.destinations.length-1){
                country+= plan.destinations[i]
                destinations.add(country)
            }
            else if (plan.destinations[i]==','){
                destinations.add(country)
                ++destNum
            }
            ++i
        }

        when (destNum) {
            1 -> {
                val spinner1Position: Int = adapter.getPosition(destinations[0])
                spinner1.setSelection(spinner1Position)
                spinner2.visibility=INVISIBLE
                spinner3.visibility=INVISIBLE
                addDestination1.visibility=VISIBLE
                addDestination2.visibility=INVISIBLE
            }
            2 -> {
                val spinner1Position: Int = adapter.getPosition(destinations[0])
                spinner1.setSelection(spinner1Position)
                val spinner2Position: Int = adapter.getPosition(destinations[1])
                spinner2.setSelection(spinner2Position)
                spinner3.visibility=INVISIBLE
                addDestination1.visibility=INVISIBLE
                addDestination2.visibility=VISIBLE
            }
            3 -> {
                val spinner1Position: Int = adapter.getPosition(destinations[0])
                spinner1.setSelection(spinner1Position)
                val spinner2Position: Int = adapter.getPosition(destinations[1])
                spinner2.setSelection(spinner2Position)
                val spinner3Position: Int = adapter.getPosition(destinations[2])
                spinner3.setSelection(spinner3Position)
                addDestination1.visibility=INVISIBLE
                addDestination2.visibility=INVISIBLE
            }
        }

        val helper= myDBhelper(applicationContext)
        val db=helper.readableDatabase

        val save=findViewById<ImageView>(R.id.saveicon)

        save.setOnClickListener {
            var updatedNotes=notes.text
            val destination=storeDestination(spinner1.selectedItem.toString(),spinner2.selectedItem.toString(),spinner3.selectedItem.toString())
            if (spinner1.selectedItem.toString()=="Ulke secilmedi.")
                Toast.makeText(this, "Varis yeri secilmedi.", Toast.LENGTH_SHORT).show()

            //--------------------------Inserting entered data into DB---------------------
            else {
                val qry = "UPDATE TRIPS SET destinations='$destination', tripStartDate=$editStartDate, tripEndDate=$editEndDate, tripNote='$updatedNotes' WHERE tripNAME='${plan.tripNAME}'"
                db.execSQL(qry)

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"Degisiklikler kaydedildi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buildDatePicker(){
        val dateRangePicker= MaterialDatePicker.Builder
            .dateRangePicker()
            .setTitleText("Tarih sec")
            .build()

        dateRangePicker.show(supportFragmentManager, "date_range_picker")

        dateRangePicker.addOnPositiveButtonClickListener { date ->
            editStartDate=date.first
            editEndDate=date.second
            selectedDate.text = "${convert.convertLongToTime(editStartDate)}  -  ${convert.convertLongToTime(editEndDate)}"
        }
    }
    //--------------------------------Storing destination--------------------------------------
    private fun storeDestination(sp1: String, sp2: String, sp3: String) : String {
        var final=sp1
        if (sp2!="Ulke secilmedi.") final+= ",$sp2"
        if (sp3!="Ulke secilmedi.") final+= ",$sp3"

        return final
    }
}