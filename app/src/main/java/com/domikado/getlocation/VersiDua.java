package com.domikado.getlocation;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VersiDua extends AppCompatActivity {

    String cityname;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versi_dua);

        textView = (TextView) findViewById(R.id.textView2);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

        Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
        Log.d("Tag","1");

        List<Address> address;
        try{
            address = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (address.size() > 0){
//                while (textView.getText().toString() == "Location"){
                    cityname = address.get(0).getLocality().toString();
                    textView.setText(cityname);
//                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
