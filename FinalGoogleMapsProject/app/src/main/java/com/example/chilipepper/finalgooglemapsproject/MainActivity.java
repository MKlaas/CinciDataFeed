package com.example.chilipepper.finalgooglemapsproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    private static String url = "https://data.cincinnati-oh.gov/resource/rg6p-b3h3.json";

    private static final String inspectionType = "insp_subtype";
    private static final String violationDescr = "violation_description";
    private static final String businessName = "business_name";
    private static final String longitude = "longitude";
    private static final String latitude = "latitude";

    ArrayList<Blip> blipList = new ArrayList<Blip>();
    ArrayList<Marker> jsonMarkers = new ArrayList<>();

    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Map");
        tabSpec.setContent(R.id.tabMap);
        tabSpec.setIndicator("Map");
        tabHost.addTab(tabSpec);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
    }

    private void populateList() {
        ArrayAdapter<Blip> adapter = new BlipListAdapter();
        lv.setAdapter(adapter);
    }

    private class BlipListAdapter extends ArrayAdapter<Blip> {

        public BlipListAdapter() {
            super (MainActivity.this, R.layout.list_activity, blipList);
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent)
        {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_activity, parent, false);

            Blip curBlip = blipList.get(pos);

            TextView iType = (TextView) view.findViewById(R.id.inspectionType);
            iType.setText(curBlip.get_inspectionType());
            TextView vDesc = (TextView) view.findViewById(R.id.violationDescr);
            vDesc.setText(curBlip.get_violationDescr());
            TextView bName = (TextView) view.findViewById(R.id.businessName);
            bName.setText(curBlip.get_businessName());

            return view;
        }
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

        for (int i = 0; i < 400; i++) {

            try {
                JSONObject c = json.getJSONObject(i);

                Blip blip = new Blip(c.getString(inspectionType), c.getString(violationDescr), c.getString(businessName),
                        Double.parseDouble(c.getString(longitude)), Double.parseDouble(c.getString(latitude)));
                String crit = "CRITICAL CONTROL POINT";

                if(blip._inspectionType.equalsIgnoreCase(crit))
                {
                    // making marker orange if it is a critical control point
                    blipList.add(blip);
                    LatLng businessMarker = new LatLng(blip.get_latitude(),blip.get_longitude());
                    gMap.addMarker(new MarkerOptions().position(businessMarker).title(blip.get_businessName()).snippet(blip.get_inspectionType()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(businessMarker));

                }
                else {
                    // if it is a standard inspection it'll be yellow
                    LatLng businessMarker = new LatLng(blip.get_latitude(),blip.get_longitude());
                    gMap.addMarker(new MarkerOptions().position(businessMarker).title(blip.get_businessName()).snippet(blip.get_inspectionType()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(businessMarker));
                }


            } catch (JSONException e)
            {
                e.printStackTrace();
            }


        }

        populateList();
    }



}