package com.domikado.getlocation;
// Created by irwancannady (irwancannady@gmail.com) on 2/21/17 at 11:32 AM.

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {

    private static final String TAG = "LocationAddress";
    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler){



        Thread thread = new Thread(){
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && addresses.size() > 0){
                        
                        Address address = addresses.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        result = sb.toString();
                    }


                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                }finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                        Log.d("uhuuyy", result);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                        Log.d("uhuuyy", result);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
