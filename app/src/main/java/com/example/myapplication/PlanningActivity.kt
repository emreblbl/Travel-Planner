package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_planning.*
import myDBhelper
import java.util.*

var startDate: Long = 0
var endDate: Long = 0

class PlanningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)



        val selectDate=findViewById<ImageView>(R.id.selectDate)

        selectDate.setOnClickListener {
            buildDatePicker()
        }


        val cancel=findViewById<ImageView>(R.id.cancelPlan)
        cancel.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        val next=findViewById<Button>(R.id.next_plan)


        val family=findViewById<CheckBox>(R.id.chFamily)
        val vacation=findViewById<CheckBox>(R.id.chVacation)
        val solo=findViewById<CheckBox>(R.id.chSolo)
        val friends=findViewById<CheckBox>(R.id.chFriends)
        val business=findViewById<CheckBox>(R.id.chBusiness)
        val roadTrip=findViewById<CheckBox>(R.id.chRoadtrip)
        val honeymoon=findViewById<CheckBox>(R.id.chHoneymoon)
        val exploration=findViewById<CheckBox>(R.id.chExploration)


        val getName=findViewById<EditText>(R.id.getName)
        val spinner1=findViewById<Spinner>(R.id.dropdownlist1)
        val spinner2=findViewById<Spinner>(R.id.dropdownlist2)

        spinner2.visibility=View.INVISIBLE
        val spinner3=findViewById<Spinner>(R.id.dropdownlist3)
        spinner3.visibility=View.INVISIBLE
        val plus1=findViewById<ImageView>(R.id.add_destination1)
        val plus2=findViewById<ImageView>(R.id.add_destination2)
        plus2.visibility=View.INVISIBLE

        val items= arrayOf("Ulke secilmedi","Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia","Australia","Austria","Azerbaijan","The Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Central African Republic","Chad","Chile","China","Colombia","Comoros","Congo","Costa Rica","Cote d'Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor (Timor-Leste)","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Fiji","Finland","France","Gabon","The Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, South","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar (Burma)","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe")
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter


        plus1.setOnClickListener {
            plus1.visibility= View.GONE
            spinner2.visibility=View.VISIBLE
            plus2.visibility=View.VISIBLE
        }

        plus2.setOnClickListener {
            plus2.visibility=View.GONE
            spinner3.visibility=View.VISIBLE
        }


        val helper= myDBhelper(applicationContext)
        val db=helper.readableDatabase
        val cv= ContentValues()
        var categories=""
        val planList: ArrayList<Trip> = helper.getTrips(this)

        next.setOnClickListener {
            //-----------------Checking if entered trip name is already in use------------------
            var nameIsUsed: Int =0
            var i=0
            while(i<planList.size){
                if (planList[i].tripNAME==getName.text.toString()) {
                    nameIsUsed=1
                    }
                ++i
                }
            //---------------------Checking which categories were selected----------------------
            if (family.isChecked)
                categories+= "Aile;"
            if (vacation.isChecked)
                categories+= "Tatil;"
            if (solo.isChecked)
                categories+= "Solo;"
            if (friends.isChecked)
                categories+= "Arkadaslar;"
            if (business.isChecked)
                categories+= "Is;"
            if (roadTrip.isChecked)
                categories+= "Yol gezisi;"
            if (honeymoon.isChecked)
                categories+= "Balayi;"
            if (exploration.isChecked)
                categories+= "Kesif ve doga yuruyusu;"

            //------------------Checking if the given name is long enough-----------------------
            if (getName.length()<3) {
                Toast.makeText(this, "Seyahat ismi cok kisa", Toast.LENGTH_SHORT).show()
                getName.requestFocus()
            }
            else if(nameIsUsed==1){
                Toast.makeText(this, "Seyahat ismi kullaniliyor.", Toast.LENGTH_SHORT).show()
                getName.requestFocus()
            }
            else if (spinner1.selectedItem.toString()=="Ulke secilmedi.")
                Toast.makeText(this, "Varis yeri secilmedi!", Toast.LENGTH_SHORT).show()

            //--------------------------Inserting entered data into DB--------------------------
                else {
                    cv.put("tripNAME", getName.text.toString())
                    cv.put("destinations", storeDestination(spinner1.selectedItem.toString(),spinner2.selectedItem.toString(),spinner3.selectedItem.toString()))
                    cv.put("tripStartDate", startDate)
                    cv.put("tripEndDate", endDate)
                    cv.put("tripNote", addnote.text.toString())
                    cv.put("categories", categories)
                    cv.put("activities", "")
                    cv.put("cities", "")
                    db.insert("TRIPS", null, cv)
                    Toast.makeText(this,"Seyahat kaydedildi.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
        }
    }

    //---------------------------------------Date picker---------------------------------------

    private fun buildDatePicker(){
        val dateRangePicker=MaterialDatePicker.Builder
            .dateRangePicker()
            .setTitleText("Tarih sec.")
            .build()

        dateRangePicker.show(supportFragmentManager, "date_range_picker")

        dateRangePicker.addOnPositiveButtonClickListener { date ->
            startDate=date.first
            endDate=date.second
            selectedDate.text = "${convert.convertLongToTime(startDate)}  -  ${convert.convertLongToTime(endDate)}"
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
