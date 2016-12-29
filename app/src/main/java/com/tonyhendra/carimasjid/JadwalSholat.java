package com.tonyhendra.carimasjid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JadwalSholat extends AppCompatActivity implements LocationListener {
    ListView lv_jadwalsholat;
    jadwaladapter jadwalAdapter;
    private LocationManager locationManager;
    private LocationListener locationListener;
    ArrayList<jadwal> jadwalArray = new ArrayList<jadwal>();
    String bestProvider,address,subuh,terbit,dzuhur,ashar,maghrib,isyak;
    TextView tv_location;
    RequestQueue requestQueue;
    Double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_location = (TextView) findViewById(R.id.tv_location);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {

            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        }
        if(lat!=null && lng!=null) {
            String mapsAPI = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyDgpNgVSd3x6zoMd9IsgD-bpBlsrv87fUI";
            Log.i("info mapsAPI", mapsAPI);
            JsonObjectRequest request_address = new JsonObjectRequest(mapsAPI,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                tv_location.setText(address);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request_address);
        }
        if(address!=null){

            String jadwalAPI = "http://ibacor.com/api/pray-times?address="+address+"&timezone=7&method=5&year=2016&month=12&day=29";
            JsonObjectRequest request_jadwal = new JsonObjectRequest(jadwalAPI, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        subuh = response.getJSONArray("results").getJSONObject(0).getString("fajr");
                        terbit = response.getJSONArray("results").getJSONObject(0).getString("sunrise");
                        dzuhur = response.getJSONArray("results").getJSONObject(0).getString("dhuhr");
                        ashar = response.getJSONArray("results").getJSONObject(0).getString("asr");
                        maghrib = response.getJSONArray("results").getJSONObject(0).getString("maghrib");
                        isyak = response.getJSONArray("results").getJSONObject(0).getString("isha");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request_jadwal);
        }

        lv_jadwalsholat = (ListView)findViewById(R.id.lv_jadwalsholat);

        jadwalArray.add(new jadwal("Subuh", subuh));
        jadwalArray.add(new jadwal("Terbit", terbit));
        jadwalArray.add(new jadwal("Dzuhur", dzuhur));
        jadwalArray.add(new jadwal("Ashar", ashar));
        jadwalArray.add(new jadwal("Maghrib", maghrib));
        jadwalArray.add(new jadwal("Isyak", isyak));

        jadwalAdapter = new jadwaladapter(JadwalSholat.this, R.layout.row,jadwalArray);
        lv_jadwalsholat.setItemsCanFocus(false);
        lv_jadwalsholat.setAdapter(jadwalAdapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
                return;

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
