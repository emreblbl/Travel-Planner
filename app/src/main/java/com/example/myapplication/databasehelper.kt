import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.myapplication.Trip

class myDBhelper (context:Context) : SQLiteOpenHelper (context, "DATABASE", null,1){
    override fun onCreate (db: SQLiteDatabase?) {
        val query="CREATE TABLE IF NOT EXISTS TRIPS (tripID INTEGER PRIMARY KEY AUTOINCREMENT, tripNAME TEXT, destinations TEXT, tripStartDate LONG, tripEndDate LONG, tripNote TEXT, categories TEXT, activities TEXT, cities TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    //----------------------------------Getting all trip data-----------------------------------

    fun getTrips(myCtx: Context) : ArrayList<Trip> {
        val qry= "SELECT * FROM TRIPS"
        val db =this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val trips=ArrayList<Trip>()

        if (cursor.count==0)
            makeText(myCtx,"Henuz plan yok!", Toast.LENGTH_SHORT).show()
        else {
            while (cursor.moveToNext()) {
                val trip = Trip()
                trip.tripID = cursor.getInt(0)
                trip.tripNAME = cursor.getString(1)
                trip.destinations=cursor.getString(2)
                trip.tripStartDate=cursor.getLong(3)
                trip.tripEndDate=cursor.getLong(4)
                trip.tripNote=cursor.getString(5)
                trip.categories=cursor.getString(6)
                trip.activities=cursor.getString(7)
                trip.cities=cursor.getString(8)
                trips.add(trip)
            }
        }
        cursor.close()
        //db.close()
        return trips
    }


    fun getTripDetails(myCtx: Context, name: String): Trip {
        val qry= "SELECT * FROM TRIPS WHERE tripNAME='$name'"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val trip=Trip()
        if (cursor.count==0)
            makeText(myCtx,"hata!", Toast.LENGTH_SHORT).show()
        else {
            cursor.moveToFirst()
            trip.tripID = cursor.getInt(0)
            trip.tripNAME = cursor.getString(1)
            trip.destinations = cursor.getString(2)
            trip.tripStartDate = cursor.getLong(3)
            trip.tripEndDate = cursor.getLong(4)
            trip.tripNote = cursor.getString(5)
            trip.categories = cursor.getString(6)
            trip.activities = cursor.getString(7)
            trip.cities = cursor.getString(8)

        }
        cursor.close()
        db.close()
        return trip
    }

    fun saveCity(cityName: String, tripName: String) {

        //----retrieving saved cities------------------------
        val old= "SELECT * FROM TRIPS WHERE tripNAME='$tripName'"
        val db1=this.readableDatabase
        val cursor=db1.rawQuery(old,null)
            cursor.moveToFirst()
            var cities=cursor.getString(8)
        cursor.close()
        db1.close()

        //---------concatenating new cities------------------

            cities+= "$cityName;"
            val db = this.writableDatabase
            val qry = "UPDATE TRIPS SET cities='$cities' WHERE tripNAME='$tripName'"
            db.execSQL(qry)
            db.close()
        }

    fun saveActivity(activityName: String, tripName: String) {

        //----retrieving saved activities------------------------
        val old= "SELECT * FROM TRIPS WHERE tripNAME='$tripName'"
        val db1=this.readableDatabase
        val cursor=db1.rawQuery(old,null)
        cursor.moveToFirst()
        var activity=cursor.getString(7)
        cursor.close()
        db1.close()

        //---------concatenating new activities------------------

        activity += "$activityName;"
        val db = this.writableDatabase
        val qry = "UPDATE TRIPS SET activities='$activity' WHERE tripNAME='$tripName'"
        db.execSQL(qry)
        db.close()
    }

    fun deletePlan(tripname: String){
        val qry= "DELETE FROM TRIPS WHERE tripNAME='$tripname'"
        val db=this.readableDatabase
        db.execSQL(qry)
    }
}
