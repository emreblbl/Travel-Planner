package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

var position=LatLng(0.0,0.0)

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val cancel=findViewById<ImageView>(R.id.backButton)
        cancel.setOnClickListener {
            val intent = Intent(applicationContext, ExploreActivity::class.java)
            startActivity(intent)
        }
        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map, mapFragment)
            .commit()

        supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val itemName=intent.getStringExtra("city").toString()
        when (itemName){
            "Istanbul" ->{position=LatLng(41.00527,28.97696
            ) }
            "Paris"->{position=LatLng(48.856613,2.352222)}
            "Vancouver"->{position=LatLng(49.260872,-123.113953)}
            "Budapest"->{position=LatLng(47.497913,19.040236)}
            "Cairo"->{position=LatLng(30.044420,31.235712)}
            "New York City"->{position=LatLng(40.730646,-73.986614)}
            "Calgary"->{position=LatLng(51.053423,-114.062589)}

            "Hilton Bosphorus - Istanbul"->{position=LatLng(41.00527,28.97696)}
            "Hotel Luxor - Cairo"->{position=LatLng(30.082661,31.202914)}
            "Boutique Hotel - Budapest"->{position=LatLng(47.488212,19.057859)}
            "Le Narcisse Blanc Hotel - Paris"->{position=LatLng(48.846197,2.272283)}
        }
    }
    //----------------------------------Displaying map----------------------------------------
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Marker")
        )
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(position,10.00F))
    }
}