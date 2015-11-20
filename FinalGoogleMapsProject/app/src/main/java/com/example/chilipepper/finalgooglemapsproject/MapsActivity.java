package com.example.chilipepper.finalgooglemapsproject;

import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    /// JSON TEST STUFF //////
    private static String url = "https://data.cincinnati-oh.gov/resource/rg6p-b3h3.json";

    private static final String inspectionType = "insp_subtype";
    private static final String violationDescr = "violation_description";
    private static final String businessName = "business_name";
    private static final String longitude = "longitude";
    private static final String latitude = "latitude";

    ArrayList<Marker> jsonlist = new ArrayList<>();
    //////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        JSONParser jParser = new JSONParser();
        JSONArray json = jParser.getJSONFromUrl(url);

        for (int i = 0; i < 20; i++) {



            try {
                JSONObject c = json.getJSONObject(i);
                String vinspectionType = c.getString(inspectionType);
                String vviolationDescr = c.getString(violationDescr);
                String vbusinessName = c.getString(businessName);
                Double vlongitude = Double.parseDouble(c.getString(longitude));
                Double vlatitude = Double.parseDouble(c.getString(latitude));

                //double tolongitude = Double.parseDouble(vlongitude);
                //double tolatitude = Double.parseDouble(vlatitude);


                LatLng businessMarker = new LatLng(vlatitude,vlongitude);
                gMap.addMarker(new MarkerOptions().position(businessMarker).title(vbusinessName).snippet(vviolationDescr));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(businessMarker));

/*
                Marker marker =  gMap.addMarker(new MarkerOptions()
                        .position(new LatLng(vlongitude, vlatitude))
                        .draggable(true)
                        .title(vbusinessName));
                jsonlist.add(marker);
*/

/*

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(inspectionType, vinspectionType);
                map.put(violationDescr, vviolationDescr);
                map.put(businessName, vbusinessName);


                jsonlist.add(map);
                */
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(39.1311062189239, -84.5175747546627);
        gMap.addMarker(new MarkerOptions().position(sydney).title("Test Marker"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }


}
